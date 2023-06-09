package me.luligabi.coxinhautilities.common.block.cake;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NetherCakeBlock extends DimensionalCakeBlock  {

    public NetherCakeBlock(Settings settings) {
        super(settings, World.NETHER);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);

        if (!world.isClient) {
            if (!player.hasVehicle() && !player.hasPassengers() && player.canUsePortals() && world.getRegistryKey() != World.NETHER) {
                player.getWorld().getProfiler().push("portal");
                //this.netherPortalTime = i;
                //player.resetNetherPortalCooldown();
                player.moveToWorld(((ServerWorld) world).getServer().getWorld(World.NETHER));
                player.getWorld().getProfiler().pop();
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