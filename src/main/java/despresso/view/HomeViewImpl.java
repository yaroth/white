package despresso.view;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import despresso.Views;
import despresso.presenter.HomeObserverInterface;
import despresso.presenter.ObserverInterface;
import org.vaadin.zhe.PaperRangeSlider;

import java.util.ArrayList;
import java.util.List;

public class HomeViewImpl extends VerticalLayout implements SubjectHomeInterface {

    private List<HomeObserverInterface> listeners = new ArrayList<>();

    HorizontalLayout line1 = new HorizontalLayout();
    HorizontalLayout line2 = new HorizontalLayout();
    HorizontalLayout line3 = new HorizontalLayout();

    private Button calendarConfirmButton;
    private PaperRangeSlider paperRangeSlider;
    private Label label;

    public HomeViewImpl() {
        label = new Label();
        showCalendarNotification();
        moodSliderHomeView();
        add(line1, line2, line3);
    }

    private void showCalendarNotification() {

        Label calendarEntry = new Label(
                "CalendarEntry");
        calendarConfirmButton = createButton(Views.DONE);

        line3.add(calendarEntry, calendarConfirmButton);
    }


    private void moodSliderHomeView() {

        Label label1 = new Label("How are you feeling today?");

        // Create a horizontal slider
        paperRangeSlider = new PaperRangeSlider(-1, 1, 0, 0);
        paperRangeSlider.setStep(1);
        paperRangeSlider.setSingleSlider(true);
        Label currentMoodLabel = new Label("Your mood is " + paperRangeSlider.getValueMax());
        //Add listener
        paperRangeSlider.addMaxValueChangeListener(event -> currentMoodLabel.setText("Your mood is " + event.getValueMax()));
        paperRangeSlider.addMaxValueChangeListener(event -> this.registerSliderObject(event));


        line1.add(label1, currentMoodLabel);
        line2.add(new Label("Negative "), paperRangeSlider, new Label("Positive "));
    }

    private Button createButton(Views view) {
        if (view.getIcon() != null){
            return new Button(view.getIcon(), event -> this.registerObject(event));
        } else {
            return new Button(view.toString(), event -> this.registerObject(event));
        }
    }

    private void registerObject(ClickEvent event) {
        for (HomeObserverInterface listener : listeners) {
            if (event.getSource().equals(calendarConfirmButton)) {
                listener.handleHomeView(Views.DONE.toString());
            }
        }
    }

    private void registerSliderObject(PaperRangeSlider.MaxValueChangeEvent event) {
        for (HomeObserverInterface listener : listeners) {
            if (event.getSource().equals(paperRangeSlider)) {
                listener.handleHomeView(Views.MOOD.toString());
            }
        }
    }

    public void setLabel(String label) {
        this.label.setText(label);
    }

    public void addConfirmationDialog(String text){

        Dialog dialog = new Dialog();
        dialog.add(new Label(text));

        dialog.setCloseOnEsc(false);
        dialog.setCloseOnOutsideClick(false);
        Label messageLabel = new Label();

        Button confirmButton;
        confirmButton = new Button("Confirm", event -> {
            messageLabel.setText("Confirmed!");
            for (HomeObserverInterface listener : listeners)
                listener.handleHomeView(Views.CONFIRM.toString());
            dialog.close();
        });

        confirmButton.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_PRIMARY);
        Button cancelButton = new Button("Cancel", event -> {
            messageLabel.setText("Cancelled...");
            for (HomeObserverInterface listener : listeners)
                listener.handleHomeView(Views.CANCEL.toString());
            dialog.close();
        });
        dialog.add(confirmButton, cancelButton);

        dialog.open();
    }


    @Override
    public void removeObserver(HomeObserverInterface observer) {
        listeners.remove(observer);

    }

    @Override
    public void addObserver(HomeObserverInterface observer) {
        listeners.add(observer);
    }
}
