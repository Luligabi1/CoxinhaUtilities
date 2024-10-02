package me.luligabi.coxinhautilities.common.block;

import com.google.common.base.Preconditions;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

/* A special implementation of BlockEntity, syncing nbt data to client, as a fix to old BEs using the now removed BlockEntityClientSerializable.
 *
 * This was made by Technici4n, so props there :)
 */
public abstract class ClientSyncedBlockEntity extends BlockEntity {

    public ClientSyncedBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public void sync(boolean shouldRemesh) {
        Preconditions.checkNotNull(level); // Maintain distinct failure case from below
        if (!(level instanceof ServerLevel serverWorld))
            throw new IllegalStateException("Cannot call sync() on the logical client! Did you check world.isClient first?");

        shouldClientRemesh = shouldRemesh | shouldClientRemesh;
        serverWorld.getChunkSource().blockChanged(worldPosition);
    }

    public void sync() {
        sync(true);
    }

    public abstract void toTag(CompoundTag nbt, HolderLookup.Provider registryLookup);

    public abstract void fromTag(CompoundTag nbt, HolderLookup.Provider registryLookup);

    public abstract void toClientTag(CompoundTag nbt, HolderLookup.Provider registryLookup);

    public abstract void fromClientTag(CompoundTag nbt, HolderLookup.Provider registryLookup);

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public final CompoundTag getUpdateTag(HolderLookup.Provider registryLookup) {
        CompoundTag nbt = super.getUpdateTag(registryLookup);
        toClientTag(nbt, registryLookup);
        nbt.putBoolean("#c", shouldClientRemesh); // mark client tag
        shouldClientRemesh = false;
        return nbt;
    }

    @Override
    protected void saveAdditional(CompoundTag nbt, HolderLookup.Provider registryLookup) {
        toTag(nbt, registryLookup);
    }

    @Override
    protected void loadAdditional(CompoundTag nbt, HolderLookup.Provider registryLookup) {
        if (nbt.contains("#c")) {
            fromClientTag(nbt, registryLookup);
            if (nbt.getBoolean("#c")) {
                remesh();
            }
        } else {
            fromTag(nbt, registryLookup);
        }
    }

    public final void remesh() {
        Preconditions.checkNotNull(level);
        if (!(level instanceof ClientLevel))
            throw new IllegalStateException("Cannot call remesh() on the server!");

        level.sendBlockUpdated(worldPosition, null, null, 0);
    }

    protected final boolean isClientSide() {
        if (level == null) {
            throw new IllegalStateException("Cannot determine if the BE is client-side if it has no level yet");
        }
        return level.isClientSide();
    }

    private boolean shouldClientRemesh = true;

}