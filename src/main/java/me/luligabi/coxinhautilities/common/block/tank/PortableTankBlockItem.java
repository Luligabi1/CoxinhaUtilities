package me.luligabi.coxinhautilities.common.block.tank;

import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRendering;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

@SuppressWarnings("UnstableApiUsage")
public class PortableTankBlockItem extends BlockItem {

    public PortableTankBlockItem(PortableTankBlock block, Settings settings) {
        super(block, settings);
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(!world.isClient()) {
            ItemStack itemStack = user.getStackInHand(hand);
            TankTier tankTier = ((PortableTankBlock) getBlock()).getTankTier();

            NbtCompound tankCompound = itemStack.getOrCreateNbt().getCompound("BlockEntityTag");
            Fluid fluid = tankCompound.get("fluidVariant") == null ? Fluids.EMPTY : FluidVariant.fromNbt(tankCompound.getCompound("fluidVariant")).getFluid();
            boolean hasSpace = (tankCompound.getLong("amount") + FluidConstants.BUCKET) <= tankTier.getCapacity();
            boolean hasBucket = tankCompound.getLong("amount") >= FluidConstants.BUCKET;

            if(!user.isSneaking() && tankCompound.getBoolean("isBucketMode")) {
                System.out.println("PORTABLE TANK: not sneaking");
                System.out.println(FluidVariantRendering.getName(FluidVariant.of(fluid)).asString());
                System.out.println("hasSpace: " + hasSpace);
                System.out.println("hasBucket: " + hasBucket);

                // FIXME: BlockHitResult behaves differently from buckets, not triggering if a block is behind the raycasted fluid.
                BlockHitResult blockHitResult = raycast(world, user, hasSpace ? RaycastContext.FluidHandling.SOURCE_ONLY : RaycastContext.FluidHandling.NONE);
                if (blockHitResult.getType() != HitResult.Type.BLOCK || blockHitResult.getType() == HitResult.Type.MISS) return TypedActionResult.pass(itemStack);
                BlockPos blockPos = blockHitResult.getBlockPos();
                Direction direction = blockHitResult.getSide();
                BlockPos blockPos2 = blockPos.offset(direction);
                if (world.canPlayerModifyAt(user, blockPos) && user.canPlaceOn(blockPos2, direction, itemStack)) {
                    if (world.getBlockState(blockPos).getBlock() instanceof FluidBlock) {
                        System.out.println("PORTABLE TANK: insert action #1");
                        if ((world.getBlockState(blockPos) == fluid.getDefaultState().getBlockState() && hasSpace) || fluid == Fluids.EMPTY) {
                            System.out.println("PORTABLE TANK: insert action #2");
                            try (Transaction transaction = Transaction.openOuter()) {
                                long amountInserted = tankCompound.getLong("amount") + FluidConstants.BUCKET;
                                tankCompound.putLong("amount", amountInserted);
                                if (fluid == Fluids.EMPTY) { // if a tank does not already have a FluidVariant set, set it.
                                    tankCompound.put("fluidVariant", FluidVariant.of(world.getFluidState(blockPos).getFluid()).toNbt());
                                }
                                if (amountInserted == tankCompound.getLong("amount")) {
                                    System.out.println("PORTABLE TANK: insert action #3");
                                    transaction.commit();
                                    world.setBlockState(blockPos, Blocks.AIR.getDefaultState());
                                    user.playSound(fluid.isIn(FluidTags.LAVA) ? SoundEvents.ITEM_BUCKET_EMPTY_LAVA : SoundEvents.ITEM_BUCKET_EMPTY, // TODO: Check if there's a way to accurately get the fluid's bucket empty sound
                                            SoundCategory.BLOCKS, 1.0F, 1.0F);
                                    Criteria.FILLED_BUCKET.trigger((ServerPlayerEntity) user, itemStack);
                                    return TypedActionResult.success(itemStack);
                                } else { /* Cancel transaction if conditions aren't met. */ }
                            }
                        }
                        return TypedActionResult.pass(itemStack);
                    } else if (world.getBlockState(blockPos).getBlock() instanceof FluidBlock && hasBucket) { // FIXME: This never triggers (possibly due to BlockHitResult's issue)
                        System.out.println("PORTABLE TANK: extract action");
                        try (Transaction transaction = Transaction.openOuter()) {
                            long amountExtracted = tankCompound.getLong("amount") - FluidConstants.BUCKET;
                            tankCompound.putLong("amount", amountExtracted);
                            if (amountExtracted == tankCompound.getLong("amount")) {
                                System.out.println("PORTABLE TANK: extract action #2");
                                if(tankCompound.getLong("amount") == 0) { // if amount reaches 0 after placing fluid outside tank, set fluidVariant to blank.
                                    tankCompound.put("fluidVariant", FluidVariant.blank().toNbt());
                                }
                                transaction.commit();
                                world.setBlockState(blockPos, fluid.getDefaultState().getBlockState());
                                /*world.playSound(null, blockPos, fluid.getBucketFillSound().isPresent() ? fluid.getBucketFillSound().get() : SoundEvents.ITEM_BUCKET_FILL,
                                    SoundCategory.BLOCKS, 1.0F, 1.0F);*/
                                fluid.getBucketFillSound().ifPresent((sound) -> user.playSound(sound, 1.0F, 1.0F));
                                Criteria.PLACED_BLOCK.trigger((ServerPlayerEntity) user, blockPos, itemStack);
                                return TypedActionResult.success(itemStack);
                            } else { /* Cancel transaction if conditions aren't met. */ }
                        }
                        return TypedActionResult.pass(itemStack);
                    }
                }
            } else {
                // Change Tank's Bucket Mode if player isn't sneaking.
                tankCompound.putBoolean("isBucketMode", !tankCompound.getBoolean("isBucketMode")); // FIXME: Can't set bucketMode if tank has no NBT
                user.sendMessage(new TranslatableText("message.coxinhautilities.tank.bucketMode")
                                        .formatted(tankTier.getPrimaryColor())
                        .append(tankCompound.getBoolean("isBucketMode") ?
                                new TranslatableText("tooltip.coxinhautilities.tank.bucketMode.on")
                                        .formatted(tankTier.getSecondaryColor()) :
                                new TranslatableText("tooltip.coxinhautilities.tank.bucketMode.off")
                                        .formatted(tankTier.getSecondaryColor())), true);
                user.playSound(tankCompound.getBoolean("isBucketMode") ?
                        SoundEvents.BLOCK_IRON_TRAPDOOR_OPEN : SoundEvents.BLOCK_IRON_TRAPDOOR_CLOSE,
                        SoundCategory.BLOCKS, 1.0F, 1.0F);
                return TypedActionResult.success(itemStack);
            }
        }
        return super.use(world, user, hand);
    }

    @Override
    protected boolean canPlace(ItemPlacementContext context, BlockState state) {
        return context.getStack().getOrCreateNbt().getCompound("BlockEntityTag").getBoolean("isBucketMode") ? false : super.canPlace(context, state);
    }
}
