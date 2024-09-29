package me.luligabi.coxinhautilities.common.item.battery;

import me.luligabi.coxinhautilities.common.item.ComponentRegistry;
import me.luligabi.coxinhautilities.common.util.IWittyComment;
import me.luligabi.coxinhautilities.common.util.Util;
import net.minecraft.component.ComponentChanges;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.api.EnergyStorage;
import team.reborn.energy.api.base.SimpleEnergyItem;

import java.util.List;

public class PotatoBatteryItem extends Item implements SimpleEnergyItem, IWittyComment {

    public PotatoBatteryItem(Settings settings) {
        super(settings
            .component(EnergyStorage.ENERGY_COMPONENT, 0L)
            .component(ComponentRegistry.ENABLED, false)
        );
    }

    @Override
    public long getEnergyCapacity(ItemStack stack) { return 4096; }

    @Override
    public long getEnergyMaxInput(ItemStack stack) { return Long.MAX_VALUE; }

    @Override
    public long getEnergyMaxOutput(ItemStack stack) { return Long.MAX_VALUE; }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(world.isClient()) return TypedActionResult.pass(user.getStackInHand(hand));
        if(user.isSneaking()) {
            ItemStack stack = user.getStackInHand(hand);
            boolean isEnabled = stack.getOrDefault(ComponentRegistry.ENABLED, false);

            ComponentChanges changes = ComponentChanges.builder()
                .add(ComponentRegistry.ENABLED, !isEnabled)
                .build();
            stack.applyChanges(changes);

            ((ServerPlayerEntity) user).networkHandler.sendPacket(
                    new PlaySoundS2CPacket(
                            Registries.SOUND_EVENT.getEntry(
                                    isEnabled ? SoundEvents.BLOCK_IRON_TRAPDOOR_CLOSE : SoundEvents.BLOCK_IRON_TRAPDOOR_OPEN
                            ),
                            SoundCategory.PLAYERS,
                            user.getX(), user.getY(), user.getZ(),
                            1.0F, 1.0F,
                            user.getBlockPos().asLong()
                    )
            );
            user.sendMessage(
                    Text.translatable("tooltip.coxinhautilities.potato_battery.3")
                                .formatted(getPrimaryColor())
                            .append(ScreenTexts.onOrOff(!isEnabled).copy()
                                .formatted(getSecondaryColor())),
                    true
            );
            return TypedActionResult.success(stack);
        }
        return super.use(world, user, hand);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(world.isClient() || !(entity instanceof PlayerEntity)) return;
        if(!stack.getOrDefault(ComponentRegistry.ENABLED, false)) return;

        Util.distributePowerToInventory((PlayerEntity) entity, stack, getEnergyMaxOutput(stack), (predicateStack) -> !(predicateStack.getItem() instanceof PotatoBatteryItem));
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        appendPowerInfo(tooltip, stack);
        addWittyComment(tooltip);
    }

    protected void appendPowerInfo(List<Text> tooltip, ItemStack stack) {
        tooltip.add(
                Text.translatable("tooltip.coxinhautilities.potato_battery.1")
                        .formatted(getPrimaryColor())
                .append(Text.translatable(
                            "tooltip.coxinhautilities.potato_battery.2",
                                Util.formatAccordingToLanguage().format(stack.get(EnergyStorage.ENERGY_COMPONENT)),
                                Util.formatAccordingToLanguage().format(getEnergyCapacity(stack))
                        ).formatted(getSecondaryColor())
                )
        );
        tooltip.add(
                Text.translatable("tooltip.coxinhautilities.potato_battery.3")
                        .formatted(getPrimaryColor())
                        .append(ScreenTexts.onOrOff(stack.getOrDefault(ComponentRegistry.ENABLED, false)).copy()
                                .formatted(getSecondaryColor()))
        );
    }

    @Override
    public List<Text> wittyComments() {
        return List.of(Text.translatable("tooltip.coxinhautilities.potato_battery.witty"));
    }

    protected Formatting getPrimaryColor() {
        return Formatting.YELLOW;
    }

    protected Formatting getSecondaryColor() {
        return Formatting.YELLOW;
    }

}