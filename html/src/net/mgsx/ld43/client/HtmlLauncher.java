package net.mgsx.ld43.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

import net.mgsx.ld43.LD43;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(LD43.SCREEN_WIDTH, LD43.SCREEN_HEIGHT);
        }

        @Override
        public ApplicationListener createApplicationListener () {
                return new LD43();
        }
}