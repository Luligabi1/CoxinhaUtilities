package me.luligabi.coxinhautilities.common.block.trashcan.energy;

import me.luligabi.coxinhautilities.common.block.BlockEntityRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import team.reborn.energy.api.base.SimpleEnergyStorage;

@SuppressWarnings("UnstableApiUsage")
public class EnergyTrashCanBlockEntity extends BlockEntity {

    public EnergyTrashCanBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.ENERGY_TRASH_CAN_BLOCK_ENTITY, pos, state);
    }

    public final SimpleEnergyStorage energyStorage = new SimpleEnergyStorage(Long.MAX_VALUE, Long.MAX_VALUE, 0) {

        @Override
        protected void onFinalCommit() {
            energyStorage.amount = 0;
        }
    };

}