package me.luligabi.coxinhautilities.common.block.tank;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class AbstrackTankBlockItem extends BlockItem {

    public AbstrackTankBlockItem(AbstractTankBlock block, Settings settings) {
        super(block, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(user.isSneaking()) {

        }
        return super.use(world, user, hand);
    }
}
