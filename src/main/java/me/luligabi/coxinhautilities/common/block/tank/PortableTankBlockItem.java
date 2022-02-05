package me.luligabi.coxinhautilities.common.block.tank;

import net.minecraft.item.BlockItem;


//@SuppressWarnings("UnstableApiUsage")
public class PortableTankBlockItem extends BlockItem {

    public PortableTankBlockItem(PortableTankBlock block, Settings settings) {
        super(block, settings);
    }

    /*@Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if(!world.isClient()) {
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
                if (blockHitResult.getType() != HitResult.Type.BLOCK) return TypedActionResult.pass(itemStack);
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
                                } else { /* Cancel transaction if conditions aren't met. */ //}
                            /*}
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
                                fluid.getBucketFillSound().ifPresent((sound) -> user.playSound(sound, 1.0F, 1.0F));
                                Criteria.PLACED_BLOCK.trigger((ServerPlayerEntity) user, blockPos, itemStack);
                                return TypedActionResult.success(itemStack);
                            } else { /* Cancel transaction if conditions aren't met. */ //}
                        /*}
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
        return TypedActionResult.fail(itemStack);
    }

    @Override
    protected boolean canPlace(ItemPlacementContext context, BlockState state) {
        return super.canPlace(context, state) && !context.getStack().getOrCreateNbt().getCompound("BlockEntityTag").getBoolean("isBucketMode");
    }*/

}
