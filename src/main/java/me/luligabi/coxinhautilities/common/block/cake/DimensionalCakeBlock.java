package me.luligabi.coxinhautilities.common.block.cake;

import net.minecraft.block.BlockState;
import net.minecraft.block.CakeBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

public class DimensionalCakeBlock extends CakeBlock {

    private final RegistryKey<World> dimension;

    public DimensionalCakeBlock(Settings settings, RegistryKey<World> dimension) {
        super(settings);
        this.dimension = dimension;
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);

        if (!world.isClient) {
            if (!player.hasVehicle() && !player.hasPassengers() && player.canUsePortals() && world.getRegistryKey() != dimension) {
                player.moveToWorld(((ServerWorld) world).getServer().getWorld(dimension));
            } else {
                return ActionResult.FAIL;
            }
        } else {
            if (itemStack.isEmpty()) {
                return ActionResult.CONSUME;
            }
        }

        return tryEat(world, pos, state, player);
    }

}