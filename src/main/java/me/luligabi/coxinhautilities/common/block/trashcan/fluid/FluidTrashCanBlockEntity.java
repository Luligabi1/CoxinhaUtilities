package me.luligabi.coxinhautilities.common.block.trashcan.fluid;

import me.luligabi.coxinhautilities.common.block.BlockEntityRegistry;
import me.luligabi.coxinhautilities.common.block.trashcan.AbstractTrashCanBlockEntity;
import me.luligabi.coxinhautilities.common.screenhandler.FluidTrashCanMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import net.neoforged.neoforge.fluids.capability.templates.VoidFluidHandler;
import org.jetbrains.annotations.Nullable;


public class FluidTrashCanBlockEntity extends AbstractTrashCanBlockEntity {

    public FluidTrashCanBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.FLUID_TRASH_CAN.get(), pos, state);
    }


    public final VoidFluidHandler fluidStorage = new VoidFluidHandler();


    public static void tick(Level world, BlockPos pos, BlockState state, FluidTrashCanBlockEntity blockEntity) {
        ItemStack stack = blockEntity.inventory.get(0);
        if(stack.isEmpty()) return;

        IFluidHandlerItem stackFluid = blockEntity.getFluidStorage(stack);
        if(stackFluid != null && stackFluid.drain(Integer.MAX_VALUE, IFluidHandler.FluidAction.SIMULATE) != FluidStack.EMPTY) {
            stackFluid.drain(Integer.MAX_VALUE, IFluidHandler.FluidAction.EXECUTE);
        }
    }

    public boolean fluidIo(Player player, InteractionHand hand) {
        return false;//FluidStorageUtil.interactWithFluidStorage(fluidStorage, player, hand);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.coxinhautilities.fluid_trash_can");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory inv, Player player) {
        return new FluidTrashCanMenu(syncId, inv, this);
    }

    @Nullable
    private IFluidHandlerItem getFluidStorage(ItemStack stack) {
        return stack.getCapability(Capabilities.FluidHandler.ITEM);
    }

}