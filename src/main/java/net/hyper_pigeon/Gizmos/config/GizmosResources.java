package net.hyper_pigeon.Gizmos.config;

import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.blockstate.JBlockModel;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.loot.JLootTable;
import net.devtech.arrp.json.recipe.*;
import net.devtech.arrp.json.tags.JTag;
import net.minecraft.util.Identifier;

import static net.devtech.arrp.json.tags.JTag.*;
import static net.hyper_pigeon.Gizmos.Gizmos.CONFIG;
import static net.hyper_pigeon.Gizmos.Gizmos.MOD_ID;

public interface GizmosResources {
    String MOD_ID_DELIMITED = MOD_ID + ":";
    RuntimeResourcePack RESOURCE_PACK = RuntimeResourcePack.create(MOD_ID_DELIMITED + "dynamic_resources");

    static void init() {
        if (CONFIG.seeker_arrows) {
            final JTag seekerArrowTag = tag().add(new Identifier(MOD_ID_DELIMITED + "eye_of_ender_arrow"));
            RESOURCE_PACK.addTag(new Identifier("items/arrows"),
                    seekerArrowTag);
            RESOURCE_PACK.addTag(new Identifier("entities/arrows"),
                    seekerArrowTag);
        }

        if (CONFIG.chorusGourdAndCultivatedShulkers) {
            final String chorusGourd = "chorus_gourd";
            addSimpleBlockState(chorusGourd);
            JLootTable chorusGourdTable = JLootTable.loot("block").pool(JLootTable.pool()
                    .rolls(1)
                        .entry(JLootTable.entry().type("item")
                            .name(MOD_ID_DELIMITED + chorusGourd))
                    .condition(JLootTable.predicate("survives_explosion")));

            RESOURCE_PACK.addLootTable(new Identifier(MOD_ID_DELIMITED + chorusGourd), chorusGourdTable);

            JRecipe chorusGourdRecipe = JRecipe.shaped(
                    JPattern.pattern(
                        "CCC",
                        "CPC",
                        "CCC"),
                    JKeys.keys()
                        .key("C", JIngredient.ingredient().item("chorus_flower"))
                        .key("P", JIngredient.ingredient().item("pumpkin")),
                    JResult.result(MOD_ID_DELIMITED + chorusGourd));

            RESOURCE_PACK.addRecipe(new Identifier(chorusGourd), chorusGourdRecipe);
        }

        if (CONFIG.seeker_arrows) {
            final String eyeOfEnderArrow = "eye_of_ender_arrow";

            JRecipe arrowRecipe = JRecipe.shaped(
                    JPattern.pattern(
                            "E",
                            "#",
                            "Y"),
                    JKeys.keys()
                            .key("E", JIngredient.ingredient().item("ender_eye"))
                            .key("#", JIngredient.ingredient().item("stick"))
                            .key("Y", JIngredient.ingredient().item("feather")),
                    JResult.stackedResult(MOD_ID_DELIMITED + eyeOfEnderArrow, 4));

            RESOURCE_PACK.addRecipe(new Identifier(MOD_ID_DELIMITED), arrowRecipe);
        }

        if (CONFIG.fireworkStarBlock) {
            final String fireworkStarBlock = "firework_star_block";

            addSimpleBlockState(fireworkStarBlock);

            JRecipe tableRecipe = JRecipe.shaped(
                    JPattern.pattern(
                            "BGG",
                            "PCP",
                            "PWP"),
                    JKeys.keys()
                            .key("B", JIngredient.ingredient().item("bowl"))
                            .key("G", JIngredient.ingredient().item("gunpowder"))
                            .key("P", JIngredient.ingredient().item("paper"))
                            .key("C", JIngredient.ingredient().item("crafting_table"))
                            .key("W", JIngredient.ingredient().tag("logs")),
                    JResult.result(MOD_ID_DELIMITED + fireworkStarBlock));

            RESOURCE_PACK.addRecipe(new Identifier(MOD_ID_DELIMITED), tableRecipe);
        }

        if (CONFIG.horseshoes) {
            final String horseshoes = "horseshoes";

            JRecipe horseshoesRecipe = JRecipe.shaped(
                    JPattern.pattern(
                            "I I",
                            "D D",
                            "IDI"),
                    JKeys.keys()
                            .key("I", JIngredient.ingredient().item("iron_ingot"))
                            .key("D", JIngredient.ingredient().item("diamond")),
                    JResult.result(MOD_ID_DELIMITED + horseshoes));

            RESOURCE_PACK.addRecipe(new Identifier(horseshoes), horseshoesRecipe);
        }

        if (CONFIG.slingShot) {
            final String slingshot = "slingshot";

            JRecipe chorusGourdRecipe = JRecipe.shaped(
                    JPattern.pattern(
                            "SSS",
                            "W W",
                            " W "),
                    JKeys.keys()
                            .key("S", JIngredient.ingredient().item("string"))
                            .key("W", JIngredient.ingredient().item("stick")),
                    JResult.result(MOD_ID_DELIMITED + slingshot));

            RESOURCE_PACK.addRecipe(new Identifier(slingshot), chorusGourdRecipe);
        }

        if (CONFIG.soulFireSpitter) {
            final String soulFireSpitter = "soul_fire_spitter";

            JRecipe spitterRecipe = JRecipe.shaped(
                    JPattern.pattern(
                            "  G",
                            " DH",
                            "SC "),
                    JKeys.keys()
                            .key("G", JIngredient.ingredient().item("glass"))
                            .key("H", JIngredient.ingredient().item("hopper"))
                            .key("D", JIngredient.ingredient().item("dispenser"))
                            .key("C", JIngredient.ingredient().item("cobblestone_wall"))
                            .key("S", JIngredient.ingredient().item("soul_torch")),
                    JResult.result(MOD_ID_DELIMITED + soulFireSpitter));

            RESOURCE_PACK.addRecipe(new Identifier(MOD_ID_DELIMITED), spitterRecipe);
        }

        RRPCallback.EVENT.register(a -> a.add(RESOURCE_PACK));
    }

    static void addSimpleBlockState(String blockName) {
        RESOURCE_PACK.addBlockState(
                JState.state(JState.variant(new JBlockModel(new Identifier(MOD_ID_DELIMITED + "block/" + blockName)))),
                new Identifier(MOD_ID_DELIMITED + blockName));
    }
}
