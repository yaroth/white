package despresso.presenter;


import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import despresso.logic.TipDuration;
import despresso.logic.TipLocation;
import despresso.logic.TipModel;
import despresso.logic.TipType;
import despresso.view.TipsViewImpl;

import javax.swing.event.ChangeEvent;
import java.sql.SQLOutput;

public class TipPresenter implements ObserverInterface{

    private TipsViewImpl tipView;
    private TipModel tipModel;

    public TipPresenter(TipsViewImpl view, TipModel tipModel) {
        this.tipModel = tipModel;
        this.tipView = view;
        this.tipView.setTipList(this.tipModel.getTipList());
        this.tipView.addObserver(this);
    }

//    public void updateRadioGroupButtons(AbstractField.ComponentValueChangeEvent<RadioButtonGroup<String>, String> event){
//
//    }


    @Override
    public void update(String viewName) {
        System.out.println("TipPresenter.update() executed...");
        this.tipModel.filterTipList(TipDuration.LONG, TipType.BODY, TipLocation.ATHOME);
        this.tipView.testLabel.setText("Some Button Clicked");
    }
}


