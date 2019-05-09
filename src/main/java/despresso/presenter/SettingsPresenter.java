package despresso.presenter;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import despresso.logic.SettingsModel;
import despresso.view.SettingsViewImpl;

public class SettingsPresenter implements ObserverInterface {

    private SettingsModel model;
    private SettingsViewImpl view;

    public SettingsPresenter(SettingsModel model, SettingsViewImpl view) {
        this.model = model;
        this.view = view;
        this.view.addObserver(this);
    }

    @Override
    public void update(ClickEvent<Button> event) {
        view.setLabel(model.doSomething());
    }
}
