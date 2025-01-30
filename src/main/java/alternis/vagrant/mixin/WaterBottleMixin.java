package alternis.vagrant.mixin;

import alternis.vagrant.registry.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.network.packet.s2c.play.SubtitleS2CPacket;
import net.minecraft.network.packet.s2c.play.TitleFadeS2CPacket;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.server.command.TitleCommand;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class WaterBottleMixin {

    @Inject(
            method = "useOnEntity",
            at = @At("HEAD"),
            cancellable = true
    )
    public void onUseOnEntity(
            ItemStack stack,
            PlayerEntity user,
            LivingEntity entity,
            Hand hand,
            CallbackInfoReturnable<ActionResult> cir
    ) {
        // Check if the interaction is on the server and valid
        if (user.getWorld().isClient()) return;

        // Verify the entity is a Warden and the item is a water bottle
        if (entity.getType() == EntityType.WARDEN && isBottle(stack)) {
            // Consume the water bottle and trigger custom action
            consumeWaterBottle(user, stack, hand);

            // Cancel further processing and return success
            cir.setReturnValue(ActionResult.SUCCESS);
        }
    }

    private boolean isBottle(ItemStack stack) {
        return stack.getItem() instanceof GlassBottleItem;
    }

    private void consumeWaterBottle(PlayerEntity user, ItemStack stack, Hand hand) {
        Text subtitle = Text.literal("Oh, he's going to be really pissed about that one.");
        // Decrement the stack if not in creative mode
        if (!user.getAbilities().creativeMode) {
            stack.decrement(1);
        }

        // Give back a glass bottle
        ItemStack blood = new ItemStack(ModItems.SCULK_BLOOD);
        if (stack.isEmpty()) {
            user.setStackInHand(hand, blood);
        } else {
            user.giveItemStack(blood);
        }

        // Trigger your custom action here
        user.getWorld().playSound(null, user.getBlockPos(), SoundEvents.ENTITY_WARDEN_ANGRY, SoundCategory.BLOCKS, 1.0F, 1.0F);
    }
}