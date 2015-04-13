package de.htwg.checkers;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

import de.htwg.checkers.view.plugin.IPlugin;
import de.htwg.checkers.view.plugin.impl.AutoFinish;
import de.htwg.checkers.view.plugin.impl.AutoMove;
import de.htwg.checkers.view.plugin.impl.MoveSuggestionPlugin;

public class PluginModule extends AbstractModule {

	@Override
	protected void configure() {
		Multibinder<IPlugin> plugins = Multibinder.newSetBinder(binder(), IPlugin.class);
		plugins.addBinding().to(MoveSuggestionPlugin.class);
		plugins.addBinding().to(AutoMove.class);
		plugins.addBinding().to(AutoFinish.class);
	}

}
