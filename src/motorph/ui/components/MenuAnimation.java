package motorph.ui.components;

import java.awt.Component;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTargetAdapter;

/**
 * This class handles the animation for showing and hiding submenus in the menu.
 * It ensures that submenus are not repeatedly displayed when clicked multiple
 * times.
 */
public class MenuAnimation {

    public static void showMenu(Component component, MenuItem item, MigLayout layout, boolean show) {
        // Create an Animator with 300ms duration
        Animator animator = new Animator(300, new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                float f = show ? fraction : 1f - fraction;
                int height = component.getPreferredSize().height;

                // Avoid zero-height animation glitches
                float animatedHeight = height * f;
                if (animatedHeight < 1f) {
                    animatedHeight = 1f; // minimum to avoid invalid constraints
                }

                // Format to avoid scientific notation
                String constraint = String.format("h %.2f!", animatedHeight);

                layout.setComponentConstraints(component, constraint);
                component.revalidate();
                item.repaint();
            }

        });
        animator.setResolution(0);
        animator.setAcceleration(.5f);
        animator.setDeceleration(.5f);
        animator.start();
    }
}
