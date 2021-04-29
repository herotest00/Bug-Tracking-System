package gui;

import constants.DateConstants;
import domain.Bug;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class DetailsController {

    @FXML private Label nameLabel, reportDateLabel, fixDateLabel, fixedByLabel, statusLabel, reportedByLabel;
    @FXML private TextArea descriptionTextArea;

    public void setData(Bug bug) {
        System.out.println(bug);
        nameLabel.setText(bug.getName());
        descriptionTextArea.setText(bug.getDescription());
        reportDateLabel.setText(bug.getReportDate().format(DateConstants.DATE_TIME_FORMATTER));
        if (bug.getFixDate() == null) {
            fixDateLabel.setText("-");
        }
        else fixDateLabel.setText(bug.getFixDate().format(DateConstants.DATE_TIME_FORMATTER));
        if (bug.getProgrammer() == null) {
            fixedByLabel.setText("None");
        }
        else fixedByLabel.setText(bug.getProgrammer().getUsername());
        reportedByLabel.setText(bug.getTester().getUsername());
        statusLabel.setText(bug.getStatus().toString());
    }
}
