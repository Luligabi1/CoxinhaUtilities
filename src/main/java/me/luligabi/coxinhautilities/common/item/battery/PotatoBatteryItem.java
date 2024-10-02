package me.luligabi.coxinhautilities.common.item.battery;

import me.luligabi.coxinhautilities.common.item.ComponentRegistry;
import me.luligabi.coxinhautilities.common.util.IWittyComment;
import me.luligabi.coxinhautilities.common.util.Util;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import team.reborn.energy.api.EnergyStorage;
import team.reborn.energy.api.base.SimpleEnergyItem;

import java.util.List;

public class PotatoBatteryItem extends Item implements SimpleEnergyItem, IWittyComment {

    public PotatoBatteryItem(Properties settings) {
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
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        if(world.isClientSide()) return InteractionResultHolder.pass(user.getItemInHand(hand));
        if(user.isShiftKeyDown()) {
            ItemStack stack = user.getItemInHand(hand);
            boolean isEnabled = stack.getOrDefault(ComponentRegistry.ENABLED, false);

            DataComponentPatch changes = DataComponentPatch.builder()
                .set(ComponentRegistry.ENABLED, !isEnabled)
                .build();
            stack.applyComponentsAndValidate(changes);

            ((ServerPlayer) user).connection.send(
                    new ClientboundSoundPacket(
                            BuiltInRegistries.SOUND_EVENT.wrapAsHolder(
                                    isEnabled ? SoundEvents.IRON_TRAPDOOR_CLOSE : SoundEvents.IRON_TRAPDOOR_OPEN
                            ),
                            SoundSource.PLAYERS,
                            user.getX(), user.getY(), user.getZ(),
                            1.0F, 1.0F,
                            user.blockPosition().asLong()
                    )
            );
            user.displayClientMessage(
                    Component.translatable("tooltip.coxinhautilities.potato_battery.3")
                                .withStyle(getPrimaryColor())
                            .append(CommonComponents.optionStatus(!isEnabled).copy()
                                .withStyle(getSecondaryColor())),
                    true
            );
            return InteractionResultHolder.success(stack);
        }
        return super.use(world, user, hand);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
        if(world.isClientSide() || !(entity instanceof Player)) return;
        if(!stack.getOrDefault(ComponentRegistry.ENABLED, false)) return;

        Util.distributePowerToInventory((Player) entity, stack, getEnergyMaxOutput(stack), (predicateStack) -> !(predicateStack.getItem() instanceof PotatoBatteryItem));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        appendPowerInfo(tooltip, stack);
        addWittyComment(tooltip);
    }

    protected void appendPowerInfo(List<Component> tooltip, ItemStack stack) {
        tooltip.add(
                Component.translatable("tooltip.coxinhautilities.potato_battery.1")
                        .withStyle(getPrimaryColor())
                .append(Component.translatable(
                            "tooltip.coxinhautilities.potato_battery.2",
                                Util.formatAccordingToLanguage().format(stack.get(EnergyStorage.ENERGY_COMPONENT)),
                                Util.formatAccordingToLanguage().format(getEnergyCapacity(stack))
                        ).withStyle(getSecondaryColor())
                )
        );
        tooltip.add(
                Component.translatable("tooltip.coxinhautilities.potato_battery.3")
                        .withStyle(getPrimaryColor())
                        .append(CommonComponents.optionStatus(stack.getOrDefault(ComponentRegistry.ENABLED, false)).copy()
                                .withStyle(getSecondaryColor()))
        );
    }

    @Override
    public List<Component> wittyComments() {
        return List.of(Component.translatable("tooltip.coxinhautilities.potato_battery.witty"));
    }

    protected ChatFormatting getPrimaryColor() {
        return ChatFormatting.YELLOW;
    }

    protected ChatFormatting getSecondaryColor() {
        return ChatFormatting.YELLOW;
    }

}