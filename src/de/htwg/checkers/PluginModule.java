package de.htwg.checkers;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

import de.htwg.checkers.view.plugin.IPlugin;
import de.htwg.checkers.view.plugin.impl.MoveSuggestionPlugin;

public class PluginModule extends AbstractModule {

	@Override
	protected void configure() {
		Multibinder<IPlugin> plugins = Multibinder.newSetBinder(binder(), IPlugin.class);
		plugins.addBinding().to(MoveSuggestionPlugin.class);
	}

}
