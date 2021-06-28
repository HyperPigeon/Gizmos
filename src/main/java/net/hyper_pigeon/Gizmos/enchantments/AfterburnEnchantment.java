package net.hyper_pigeon.Gizmos.enchantments;

//import com.chocohead.mm.api.ClassTinkerers;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class AfterburnEnchantment extends Enchantment {
    protected AfterburnEnchantment(Rarity weight, EnchantmentTarget type, EquipmentSlot[] slotTypes) {
        super(weight, type, slotTypes);
    }
//    public AfterburnEnchantment(Rarity weight,  EquipmentSlot[] slotTypes) {
//        super(weight, ClassTinkerers.getEnum(EnchantmentTarget.class, "SOUL_FIRE_SPITTER"), slotTypes);
//    }
//
//    public int getMinPower(int level) {
//        return 1 + (level - 1) * 10;
//    }
//
//    public int getMaxPower(int level) {
//        return this.getMinPower(level) + 15;
//    }
//
//    public int getMaxLevel() {
//        return 5;
//    }
}
