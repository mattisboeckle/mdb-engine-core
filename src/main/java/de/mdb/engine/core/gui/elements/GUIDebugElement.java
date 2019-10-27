package de.mdb.engine.core.gui.elements;

import static org.lwjgl.nuklear.Nuklear.NK_RGBA;
import static org.lwjgl.nuklear.Nuklear.NK_TEXT_LEFT;
import static org.lwjgl.nuklear.Nuklear.NK_WINDOW_BORDER;
import static org.lwjgl.nuklear.Nuklear.NK_WINDOW_MINIMIZABLE;
import static org.lwjgl.nuklear.Nuklear.NK_WINDOW_MOVABLE;
import static org.lwjgl.nuklear.Nuklear.nk_begin;
import static org.lwjgl.nuklear.Nuklear.nk_button_label;
import static org.lwjgl.nuklear.Nuklear.nk_color_picker;
import static org.lwjgl.nuklear.Nuklear.nk_combo_begin_color;
import static org.lwjgl.nuklear.Nuklear.nk_combo_end;
import static org.lwjgl.nuklear.Nuklear.nk_end;
import static org.lwjgl.nuklear.Nuklear.nk_label;
import static org.lwjgl.nuklear.Nuklear.nk_layout_row_dynamic;
import static org.lwjgl.nuklear.Nuklear.nk_layout_row_static;
import static org.lwjgl.nuklear.Nuklear.nk_property_float;
import static org.lwjgl.nuklear.Nuklear.nk_propertyf;
import static org.lwjgl.nuklear.Nuklear.nk_rect;
import static org.lwjgl.nuklear.Nuklear.nk_rgb_cf;
import static org.lwjgl.nuklear.Nuklear.nk_widget_width;

import org.lwjgl.nuklear.NkColor;
import org.lwjgl.nuklear.NkColorf;
import org.lwjgl.nuklear.NkContext;
import org.lwjgl.nuklear.NkRect;
import org.lwjgl.nuklear.NkVec2;
import org.lwjgl.system.MemoryStack;

import de.mdb.engine.core.GameEngine;
import de.mdb.engine.core.util.Clock;

public class GUIDebugElement extends GUIElement {

	public GUIDebugElement() {
		super("Debug");
		x = 20;
		y = 20;
	}
	
	NkColorf background = NkColorf.create().r(0.10f).g(0.18f).b(0.24f).a(1.0f);
	
	float[] flySpeed = {2.0f};

	@Override
	public void layout(NkContext context) {
		
		style.applyStyle(context);
		
		try(MemoryStack stack = MemoryStack.stackPush())
		{
			
			NkRect rect = NkRect.mallocStack(stack);
			
			if(nk_begin(context, name, nk_rect(x, y, 200, 250, rect),
					NK_WINDOW_BORDER | NK_WINDOW_MOVABLE | NK_WINDOW_MINIMIZABLE))
			{	
				nk_layout_row_dynamic(context, 30, 1);
				nk_label(context, "FPS: " + Clock.getAverageFPS(), NK_TEXT_LEFT);
				
				nk_layout_row_dynamic(context, 30, 1);
				nk_property_float(context, "Flying speed", 1f, flySpeed, 10, 0.5f, 0.05f);
				
				nk_layout_row_dynamic(context, 20, 1);
				nk_label(context, "Background:", NK_TEXT_LEFT);
				nk_layout_row_dynamic(context, 25, 1);
				if (nk_combo_begin_color(context, nk_rgb_cf(background, NkColor.mallocStack(stack)),
						NkVec2.mallocStack(stack).set(nk_widget_width(context), 400))) {
					nk_layout_row_dynamic(context, 120, 1);
					nk_color_picker(context, background, NK_RGBA);
					nk_layout_row_dynamic(context, 25, 1);
					background.r(nk_propertyf(context, "#R:", 0, background.r(), 1.0f, 0.01f, 0.005f))
							.g(nk_propertyf(context, "#G:", 0, background.g(), 1.0f, 0.01f, 0.005f))
							.b(nk_propertyf(context, "#B:", 0, background.b(), 1.0f, 0.01f, 0.005f))
							.a(nk_propertyf(context, "#A:", 0, background.a(), 1.0f, 0.01f, 0.005f));
					nk_combo_end(context);
				}
				
				
				
				nk_layout_row_static(context, 30, 80, 1);
				if(nk_button_label(context, "Exit"))
				{
					GameEngine.stop();
				}
			}
			nk_end(context);
		}
	}
	
	public NkColorf getBackground()
	{
		return background;
	}
	
	public float getFlySpeed()
	{
		return flySpeed[0];
	}
	
}