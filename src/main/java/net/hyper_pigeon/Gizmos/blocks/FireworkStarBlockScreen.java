package net.hyper_pigeon.Gizmos.blocks;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookProvider;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class FireworkStarBlockScreen extends HandledScreen<FireworkStarBlockCraftingScreenHandler> implements RecipeBookProvider {
    private static final Identifier TEXTURE = new Identifier("gizmos","textures/gui/container/firework_star_block_gui_main.png");
    private static final Identifier RECIPE_BUTTON_TEXTURE = new Identifier("textures/gui/recipe_button.png");
    private final RecipeBookWidget recipeBook = new RecipeBookWidget();
    private boolean narrow;


    public FireworkStarBlockScreen(FireworkStarBlockCraftingScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    protected void init() {
        super.init();
        this.backgroundWidth = 200;
        this.backgroundHeight = 200;
        this.narrow = this.width < 379;
//        this.recipeBook.initialize(this.width, this.height, this.client, this.narrow, (AbstractRecipeScreenHandler)this.handler);
//        this.x = this.recipeBook.findLeftEdge(this.width, this.backgroundWidth);
//        this.addDrawableChild(new TexturedButtonWidget(this.x + 5, this.height / 2 - 49, 20, 18, 0, 0, 19, RECIPE_BUTTON_TEXTURE, (button) -> {
//            this.recipeBook.toggleOpen();
//            this.x = this.recipeBook.findLeftEdge(this.width, this.backgroundWidth);
//            ((TexturedButtonWidget)button).setPos(this.x + 5, this.height / 2 - 49);
//        }));
//        this.addSelectableChild(this.recipeBook);
//        this.setInitialFocus(this.recipeBook);

//        this.addSelectableChild(this.recipeBook);
//        this.setInitialFocus(this.recipeBook);
//        this.addButton(new TexturedButtonWidget(this.x + 5, this.height / 2 - 49, 20, 18, 0, 0, 19, RECIPE_BUTTON_TEXTURE, (buttonWidget) -> {
//            this.recipeBook.reset(this.narrow);
//            this.recipeBook.toggleOpen();
//            this.x = this.recipeBook.findLeftEdge(this.narrow, this.width, this.backgroundWidth);
//            ((TexturedButtonWidget)buttonWidget).setPos(this.x + 5, this.height / 2 - 49);
//        }));

        this.titleX = 11;
        this.titleY = -9;
        this.playerInventoryTitleX = 10;
        this.playerInventoryTitleY = this.backgroundHeight - 115;
    }

//    public void tick() {
//        super.tick();
//        //this.recipeBook.update();
//    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        if (this.recipeBook.isOpen() && this.narrow) {
            this.drawBackground(matrices, delta, mouseX, mouseY);
            this.recipeBook.render(matrices, mouseX, mouseY, delta);
        } else {
            this.recipeBook.render(matrices, mouseX, mouseY, delta);
            super.render(matrices, mouseX, mouseY, delta);
            this.recipeBook.drawGhostSlots(matrices, this.x, this.y, true, delta);
        }

        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
        this.recipeBook.drawTooltip(matrices, this.x, this.y, mouseX, mouseY);
    }

    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int i = this.x;
        int j = (this.height - this.backgroundHeight) / 2;
        this.drawTexture(matrices, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
    }

    protected boolean isPointWithinBounds(int xPosition, int yPosition, int width, int height, double pointX, double pointY) {
        return (!this.narrow || !this.recipeBook.isOpen()) && super.isPointWithinBounds(xPosition, yPosition, width, height, pointX, pointY);
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.recipeBook.mouseClicked(mouseX, mouseY, button)) {
            this.setFocused(this.recipeBook);
            return true;
        } else {
            return this.narrow && this.recipeBook.isOpen() ? true : super.mouseClicked(mouseX, mouseY, button);
        }
    }

    protected boolean isClickOutsideBounds(double mouseX, double mouseY, int left, int top, int button) {
        boolean bl = mouseX < (double)left || mouseY < (double)top || mouseX >= (double)(left + this.backgroundWidth) || mouseY >= (double)(top + this.backgroundHeight);
        return this.recipeBook.isClickOutsideBounds(mouseX, mouseY, this.x, this.y, this.backgroundWidth, this.backgroundHeight, button) && bl;
    }

    protected void onMouseClick(Slot slot, int invSlot, int clickData, SlotActionType actionType) {
        super.onMouseClick(slot, invSlot, clickData, actionType);
        //this.recipeBook.slotClicked(slot);
    }

    public void refreshRecipeBook() {
        this.recipeBook.refresh();
    }

    public void removed() {
        //this.recipeBook.close();
        super.removed();
    }

    public RecipeBookWidget getRecipeBookWidget() {
        return this.recipeBook;
    }

//    protected void init() {
//        super.init();
//        this.narrow = this.width < 379;
//        this.titleX = 29;
//    }
//
//    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
//        this.renderBackground(matrices);
//        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
//    }
//
//    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
//        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
//        this.client.getTextureManager().bindTexture(TEXTURE);
//        int i = this.x;
//        int j = (this.height - this.backgroundHeight) / 2;
//        this.drawTexture(matrices, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
//    }

}
