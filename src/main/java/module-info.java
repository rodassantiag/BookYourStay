module co.edu.uniquindio.bookyourstay {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires org.simplejavamail;
    requires org.simplejavamail.core;
    requires twilio;


    opens co.edu.uniquindio.bookyourstay to javafx.fxml;
    exports co.edu.uniquindio.bookyourstay;
    exports co.edu.uniquindio.bookyourstay.controladores;
    opens co.edu.uniquindio.bookyourstay.controladores to javafx.fxml;
}