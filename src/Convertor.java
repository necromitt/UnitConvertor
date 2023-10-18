import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Convertor {
    private JTextField txt1;
    private JPanel ConvertorPan;
    private JPanel valueOut;
    private JComboBox<String> cmboInput2;
    private JComboBox<String> cmboInput1;
    private JButton btnClick;
    private JLabel out1;
    private JLabel out2;
    private JLabel out3;
    private JLabel out4;
    private JLabel out5;
    private JLabel out6;
    private JLabel out7;
    private JLabel out8;
    private double inin;


    String[] select = new String[]{"Select", "Distance", "Energy", "Power", "Temperature", "Pressure"};
    String[] select2 = new String[]{""};
    String[] valuesDist = new String[]{"mm", "cm", "dm", "m", "km", "ft", "yd", "ml"};
    String[] valuesPower = new String[]{"W", "kW", "HP"};
    String[] valuesEnergy = new String[]{"J", "kJ", "MJ", "Wh", "kWh", "MWh"};
    String[] valuesTemp = new String[]{"°C", "°F", "°K"};
    String[] valuesPress = new String[]{"bar", "Pa", "kPa", "MPa", "psi"};


    List<? extends Number> distListMM = Arrays.asList(1, 0.1, 0.01, 0.001, 0.000001, 0.003280839895, 0.0010936, 0.00000062); //done
    List<? extends Number> distListCM = Arrays.asList(10, 1, 0.1, 0.01, 0.00001, 0.03280839895, 0.010936, 0.00000621); //done
    List<? extends Number> distListDM = Arrays.asList(100, 10, 1, 0.1, 0.0001, 0.3280839895, 0.1093613298, 0.0000621371); //done
    List<? extends Number> distListM = Arrays.asList(1000, 100, 10, 1, 0.001, 3.28084, 1.0936133, 0.000621371192); //done
    List<? extends Number> distListKM = Arrays.asList(1000000, 100000, 10000, 1000, 1, 3280.8399, 1093.6132983377, 0.621371192); //done
    List<? extends Number> distListFT = Arrays.asList(304, 8, 30.48, 3.048, 0.3048, 0.0003048, 1, 0.333333333, 0.000189394); //done
    List<? extends Number> distListYD = Arrays.asList(914.4, 91.44, 9.144, 0.9144, 0.0009144, 3, 1, 0.000568182); //done
    List<? extends Number> distListML = Arrays.asList(1609340, 160934, 16093.4, 1609.34, 1.60934, 5279.99, 1760, 1); //done

    List<? extends Number> powerListW = Arrays.asList(1, 0.001, 0.001); //done
    List<? extends Number> powerListkW = Arrays.asList(1000, 1, 1.34); //done
    List<? extends Number> powerListHP = Arrays.asList(746, 0.746, 1); //done

    List<? extends Number> energyListJ = Arrays.asList(1, 0.001, 0.000001, 0.0002777778, 0.0000002778, 0.0000000002778); //done
    List<? extends Number> energyListkJ = Arrays.asList(1000, 1, 0.001, 0.2777777778, 0.0002777778, 0.00000027778); //done
    List<? extends Number> energyListMJ = Arrays.asList(1000000, 1000, 1, 277.778, 0.278, 0.000278); //done
    List<? extends Number> energyListWh = Arrays.asList(3600, 3.6, 0.0036, 1, 0.001, 0.000001); //done
    List<? extends Number> energyListkWh = Arrays.asList(3600000, 3600, 3.6, 1000, 1, 0.001); //done
    List<? extends Number> energyListMWh = Arrays.asList(0x36ee80, 3600000, 3600, 1000000, 1000, 1); //done -> j to MWh hexadecimal

    List<? extends Number> tempListC = Arrays.asList(1, 33.7999992, 274.1499939); //done
    List<? extends Number> tempListF = Arrays.asList(-17.2222195, 1, 255.9277802); //done
    List<? extends Number> tempListK = Arrays.asList(-272.1499939, -457.8699951, 1); //done

    List<? extends Number> pressListBar = Arrays.asList(1, 100000, 100, 0.1, 14.5037681); //done
    List<? extends Number> pressListPa = Arrays.asList(0.00001, 1, 0.001, 0.000001, 0.0001450377); //done
    List<? extends Number> pressListkPa = Arrays.asList(0.01, 1000, 0.001, 1, 0.1450377377); //done
    List<? extends Number> pressListMPa = Arrays.asList(10, 1000000, 1000, 1, 145.03773773); //done
    List<? extends Number> pressListPsi = Arrays.asList(0.0689475729, 6894.7572932, 6.8947572932, 0.0068947573, 1); //done


    public Convertor() {


        txt1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                char c = e.getKeyChar();


                if (!((c >= '0') && (c <= '9') || (c == ',') ||
                        (c == KeyEvent.VK_BACK_SPACE) ||
                        (c == KeyEvent.VK_DELETE))) {
                    e.consume();
                }
            }
        });
        btnClick.addActionListener(actionEvent -> {

            cmboInput1.setModel(new DefaultComboBoxModel<>(select));
            cmboInput2.setModel(new DefaultComboBoxModel<>(select2));
            txt1.setText("");
            out1.setText("");
            out2.setText("");
            out3.setText("");
            out4.setText("");
            out5.setText("");
            out6.setText("");
            out7.setText("");
            out8.setText("");
        });


        cmboInput1.addActionListener(actionEvent -> {
            String s = (String) cmboInput1.getSelectedItem();
            assert s != null;
            switch (s) {
                case "Distance" -> cmboInput2.setModel(new DefaultComboBoxModel<>(valuesDist));
                case "Power" -> cmboInput2.setModel(new DefaultComboBoxModel<>(valuesPower));
                case "Energy" -> cmboInput2.setModel(new DefaultComboBoxModel<>(valuesEnergy));
                case "Temperature" -> cmboInput2.setModel(new DefaultComboBoxModel<>(valuesTemp));
                case "Pressure" -> cmboInput2.setModel(new DefaultComboBoxModel<>(valuesPress));
                case "Select" -> cmboInput2.setModel(new DefaultComboBoxModel<>(select2));
            }
        });


        cmboInput2.addActionListener(actionEvent -> {

            inin = Double.parseDouble(txt1.getText());

            List<Double> multipliedList = new ArrayList<>();

            String selection1 = (String) cmboInput1.getSelectedItem();
            String selection2 = (String) cmboInput2.getSelectedItem();
            assert selection1 != null;
            assert selection2 != null;


            if (txt1.getText().contains(",")) {
                Pattern decimalPattern = Pattern.compile("\\d+\\.\\d+");
                Matcher matcher = decimalPattern.matcher(txt1.getText());
                String decimalString = matcher.group();

                double finNum = Double.parseDouble(decimalString);
                System.out.println("Haha");
            }


            try {
                if (selection1.equals("Distance")) {

                    switch (selection2) {
                        case "mm" -> {

                            for (Number n : distListMM) {
                                multipliedList.add(n.doubleValue() * inin);
                            }

                            out1.setText("");
                            out2.setText("");
                            out3.setText("");
                            out4.setText("");
                            out5.setText("");
                            out6.setText("");
                            out7.setText("");
                            out8.setText("");

                            out1.setForeground(Color.red);
                            out2.setForeground(Color.black);
                            out3.setForeground(Color.black);
                            out4.setForeground(Color.black);
                            out5.setForeground(Color.black);
                            out6.setForeground(Color.black);
                            out7.setForeground(Color.black);
                            out8.setForeground(Color.black);

                            out1.setText(multipliedList.get(0) + " " + valuesDist[0]);
                            out2.setText(multipliedList.get(1) + " " + valuesDist[1]);
                            out3.setText(multipliedList.get(2) + " " + valuesDist[2]);
                            out4.setText(multipliedList.get(3) + " " + valuesDist[3]);
                            out5.setText(multipliedList.get(4) + " " + valuesDist[4]);
                            out6.setText(multipliedList.get(5) + " " + valuesDist[5]);
                            out7.setText(multipliedList.get(6) + " " + valuesDist[6]);
                            out8.setText(multipliedList.get(7) + " " + valuesDist[7]);


                            multipliedList.removeIf(n -> n < 1);
                        }
                        case "cm" -> {
                            for (Number n : distListCM) {
                                multipliedList.add(n.doubleValue() * inin);
                            }

                            out1.setText("");
                            out2.setText("");
                            out3.setText("");
                            out4.setText("");
                            out5.setText("");
                            out6.setText("");
                            out7.setText("");
                            out8.setText("");

                            out1.setForeground(Color.black);
                            out2.setForeground(Color.red);
                            out3.setForeground(Color.black);
                            out4.setForeground(Color.black);
                            out5.setForeground(Color.black);
                            out6.setForeground(Color.black);
                            out7.setForeground(Color.black);
                            out8.setForeground(Color.black);

                            out1.setText(multipliedList.get(0) + " " + valuesDist[0]);
                            out2.setText(multipliedList.get(1) + " " + valuesDist[1]);
                            out3.setText(multipliedList.get(2) + " " + valuesDist[2]);
                            out4.setText(multipliedList.get(3) + " " + valuesDist[3]);
                            out5.setText(multipliedList.get(4) + " " + valuesDist[4]);
                            out6.setText(multipliedList.get(5) + " " + valuesDist[5]);
                            out7.setText(multipliedList.get(6) + " " + valuesDist[6]);
                            out8.setText(multipliedList.get(7) + " " + valuesDist[7]);
                            multipliedList.removeIf(n -> n < 1);
                        }
                        case "dm" -> {

                            for (Number n : distListDM) {
                                multipliedList.add(n.doubleValue() * inin);
                            }

                            out1.setText("");
                            out2.setText("");
                            out3.setText("");
                            out4.setText("");
                            out5.setText("");
                            out6.setText("");
                            out7.setText("");
                            out8.setText("");

                            out1.setForeground(Color.black);
                            out2.setForeground(Color.black);
                            out3.setForeground(Color.red);
                            out4.setForeground(Color.black);
                            out5.setForeground(Color.black);
                            out6.setForeground(Color.black);
                            out7.setForeground(Color.black);
                            out8.setForeground(Color.black);

                            out1.setText(multipliedList.get(0) + " " + valuesDist[0]);
                            out2.setText(multipliedList.get(1) + " " + valuesDist[1]);
                            out3.setText(multipliedList.get(2) + " " + valuesDist[2]);
                            out4.setText(multipliedList.get(3) + " " + valuesDist[3]);
                            out5.setText(multipliedList.get(4) + " " + valuesDist[4]);
                            out6.setText(multipliedList.get(5) + " " + valuesDist[5]);
                            out7.setText(multipliedList.get(6) + " " + valuesDist[6]);
                            out8.setText(multipliedList.get(7) + " " + valuesDist[7]);
                            multipliedList.removeIf(n -> n < 1);
                        }
                        case "m" -> {

                            for (Number n : distListM) {
                                multipliedList.add(n.doubleValue() * inin);
                            }

                            out1.setText("");
                            out2.setText("");
                            out3.setText("");
                            out4.setText("");
                            out5.setText("");
                            out6.setText("");
                            out7.setText("");
                            out8.setText("");

                            out1.setForeground(Color.black);
                            out2.setForeground(Color.black);
                            out3.setForeground(Color.black);
                            out4.setForeground(Color.red);
                            out5.setForeground(Color.black);
                            out6.setForeground(Color.black);
                            out7.setForeground(Color.black);
                            out8.setForeground(Color.black);

                            out1.setText(multipliedList.get(0) + " " + valuesDist[0]);
                            out2.setText(multipliedList.get(1) + " " + valuesDist[1]);
                            out3.setText(multipliedList.get(2) + " " + valuesDist[2]);
                            out4.setText(multipliedList.get(3) + " " + valuesDist[3]);
                            out5.setText(multipliedList.get(4) + " " + valuesDist[4]);
                            out6.setText(multipliedList.get(5) + " " + valuesDist[5]);
                            out7.setText(multipliedList.get(6) + " " + valuesDist[6]);
                            out8.setText(multipliedList.get(7) + " " + valuesDist[7]);
                            multipliedList.removeIf(n -> n < 1);
                        }
                        case "km" -> {

                            for (Number n : distListKM) {
                                multipliedList.add(n.doubleValue() * inin);
                            }

                            out1.setText("");
                            out2.setText("");
                            out3.setText("");
                            out4.setText("");
                            out5.setText("");
                            out6.setText("");
                            out7.setText("");
                            out8.setText("");

                            out1.setForeground(Color.black);
                            out2.setForeground(Color.black);
                            out3.setForeground(Color.black);
                            out4.setForeground(Color.black);
                            out5.setForeground(Color.red);
                            out6.setForeground(Color.black);
                            out7.setForeground(Color.black);
                            out8.setForeground(Color.black);

                            out1.setText(multipliedList.get(0) + " " + valuesDist[0]);
                            out2.setText(multipliedList.get(1) + " " + valuesDist[1]);
                            out3.setText(multipliedList.get(2) + " " + valuesDist[2]);
                            out4.setText(multipliedList.get(3) + " " + valuesDist[3]);
                            out5.setText(multipliedList.get(4) + " " + valuesDist[4]);
                            out6.setText(multipliedList.get(5) + " " + valuesDist[5]);
                            out7.setText(multipliedList.get(6) + " " + valuesDist[6]);
                            out8.setText(multipliedList.get(7) + " " + valuesDist[7]);
                            multipliedList.removeIf(n -> n < 1);
                        }
                        case "ft" -> {

                            for (Number n : distListFT) {
                                multipliedList.add(n.doubleValue() * inin);
                            }

                            out1.setText("");
                            out2.setText("");
                            out3.setText("");
                            out4.setText("");
                            out5.setText("");
                            out6.setText("");
                            out7.setText("");
                            out8.setText("");

                            out1.setForeground(Color.black);
                            out2.setForeground(Color.black);
                            out3.setForeground(Color.black);
                            out4.setForeground(Color.black);
                            out5.setForeground(Color.black);
                            out6.setForeground(Color.red);
                            out7.setForeground(Color.black);
                            out8.setForeground(Color.black);

                            out1.setText(multipliedList.get(0) + " " + valuesDist[0]);
                            out2.setText(multipliedList.get(1) + " " + valuesDist[1]);
                            out3.setText(multipliedList.get(2) + " " + valuesDist[2]);
                            out4.setText(multipliedList.get(3) + " " + valuesDist[3]);
                            out5.setText(multipliedList.get(4) + " " + valuesDist[4]);
                            out6.setText(multipliedList.get(5) + " " + valuesDist[5]);
                            out7.setText(multipliedList.get(6) + " " + valuesDist[6]);
                            out8.setText(multipliedList.get(7) + " " + valuesDist[7]);
                            multipliedList.removeIf(n -> n < 1);
                        }
                        case "yd" -> {

                            for (Number n : distListYD) {
                                multipliedList.add(n.doubleValue() * inin);
                            }

                            out1.setText("");
                            out2.setText("");
                            out3.setText("");
                            out4.setText("");
                            out5.setText("");
                            out6.setText("");
                            out7.setText("");
                            out8.setText("");

                            out1.setForeground(Color.black);
                            out2.setForeground(Color.black);
                            out3.setForeground(Color.black);
                            out4.setForeground(Color.black);
                            out5.setForeground(Color.black);
                            out6.setForeground(Color.black);
                            out7.setForeground(Color.red);
                            out8.setForeground(Color.black);

                            out1.setText(multipliedList.get(0) + " " + valuesDist[0]);
                            out2.setText(multipliedList.get(1) + " " + valuesDist[1]);
                            out3.setText(multipliedList.get(2) + " " + valuesDist[2]);
                            out4.setText(multipliedList.get(3) + " " + valuesDist[3]);
                            out5.setText(multipliedList.get(4) + " " + valuesDist[4]);
                            out6.setText(multipliedList.get(5) + " " + valuesDist[5]);
                            out7.setText(multipliedList.get(6) + " " + valuesDist[6]);
                            out8.setText(multipliedList.get(7) + " " + valuesDist[7]);
                            multipliedList.removeIf(n -> n < 1);
                        }
                        case "ml" -> {

                            for (Number n : distListML) {
                                multipliedList.add(n.doubleValue() * inin);
                            }

                            out1.setText("");
                            out2.setText("");
                            out3.setText("");
                            out4.setText("");
                            out5.setText("");
                            out6.setText("");
                            out7.setText("");
                            out8.setText("");

                            out1.setForeground(Color.black);
                            out2.setForeground(Color.black);
                            out3.setForeground(Color.black);
                            out4.setForeground(Color.black);
                            out5.setForeground(Color.black);
                            out6.setForeground(Color.black);
                            out7.setForeground(Color.black);
                            out8.setForeground(Color.red);

                            out1.setText(multipliedList.get(0) + " " + valuesDist[0]);
                            out2.setText(multipliedList.get(1) + " " + valuesDist[1]);
                            out3.setText(multipliedList.get(2) + " " + valuesDist[2]);
                            out4.setText(multipliedList.get(3) + " " + valuesDist[3]);
                            out5.setText(multipliedList.get(4) + " " + valuesDist[4]);
                            out6.setText(multipliedList.get(5) + " " + valuesDist[5]);
                            out7.setText(multipliedList.get(6) + " " + valuesDist[6]);
                            out8.setText(multipliedList.get(7) + " " + valuesDist[7]);
                            multipliedList.removeIf(n -> n < 1);
                        }
                    }

                }
                if (selection1.equals("Power")) {
                    switch (selection2) {
                        case "W" -> {
                            for (Number n : powerListW) {
                                multipliedList.add(n.doubleValue() * inin);
                            }

                            out1.setText("");
                            out2.setText("");
                            out3.setText("");
                            out4.setText("");
                            out5.setText("");
                            out6.setText("");
                            out7.setText("");
                            out8.setText("");

                            out1.setForeground(Color.red);
                            out2.setForeground(Color.black);
                            out3.setForeground(Color.black);

                            out1.setText(multipliedList.get(0) + " " + valuesPower[0]);
                            out2.setText(multipliedList.get(1) + " " + valuesPower[1]);
                            out3.setText(multipliedList.get(2) + " " + valuesPower[2]);
                            multipliedList.removeIf(n -> n < 1);
                        }
                        case "kW" -> {
                            for (Number n : powerListkW) {
                                multipliedList.add(n.doubleValue() * inin);
                            }

                            out1.setText("");
                            out2.setText("");
                            out3.setText("");
                            out4.setText("");
                            out5.setText("");
                            out6.setText("");
                            out7.setText("");
                            out8.setText("");

                            out1.setForeground(Color.black);
                            out2.setForeground(Color.red);
                            out3.setForeground(Color.black);

                            out1.setText(multipliedList.get(0) + " " + valuesPower[0]);
                            out2.setText(multipliedList.get(1) + " " + valuesPower[1]);
                            out3.setText(multipliedList.get(2) + " " + valuesPower[2]);
                            multipliedList.removeIf(n -> n < 1);
                        }
                        case "HP" -> {
                            for (Number n : powerListHP) {
                                multipliedList.add(n.doubleValue() * inin);
                            }

                            out1.setText("");
                            out2.setText("");
                            out3.setText("");
                            out4.setText("");
                            out5.setText("");
                            out6.setText("");
                            out7.setText("");
                            out8.setText("");

                            out1.setForeground(Color.black);
                            out2.setForeground(Color.black);
                            out3.setForeground(Color.red);

                            out1.setText(multipliedList.get(0) + " " + valuesPower[0]);
                            out2.setText(multipliedList.get(1) + " " + valuesPower[1]);
                            out3.setText(multipliedList.get(2) + " " + valuesPower[2]);
                            multipliedList.removeIf(n -> n < 1);
                        }
                    }
                }
                if (selection1.equals("Energy")) {
                    switch (selection2) {
                        case "J" -> {

                            for (Number n : energyListJ) {
                                multipliedList.add(n.doubleValue() * inin);
                            }

                            out1.setText("");
                            out2.setText("");
                            out3.setText("");
                            out4.setText("");
                            out5.setText("");
                            out6.setText("");
                            out7.setText("");
                            out8.setText("");

                            out1.setForeground(Color.red);
                            out2.setForeground(Color.black);
                            out3.setForeground(Color.black);
                            out4.setForeground(Color.black);
                            out5.setForeground(Color.black);
                            out6.setForeground(Color.black);


                            out1.setText(multipliedList.get(0) + " " + valuesEnergy[0]);
                            out2.setText(multipliedList.get(1) + " " + valuesEnergy[1]);
                            out3.setText(multipliedList.get(2) + " " + valuesEnergy[2]);
                            out4.setText(multipliedList.get(3) + " " + valuesEnergy[3]);
                            out5.setText(multipliedList.get(4) + " " + valuesEnergy[4]);
                            out6.setText(multipliedList.get(5) + " " + valuesEnergy[5]);
                            multipliedList.removeIf(n -> n < 1);
                        }
                        case "kJ" -> {

                            for (Number n : energyListkJ) {
                                multipliedList.add(n.doubleValue() * inin);
                            }

                            out1.setText("");
                            out2.setText("");
                            out3.setText("");
                            out4.setText("");
                            out5.setText("");
                            out6.setText("");
                            out7.setText("");
                            out8.setText("");

                            out1.setForeground(Color.black);
                            out2.setForeground(Color.red);
                            out3.setForeground(Color.black);
                            out4.setForeground(Color.black);
                            out5.setForeground(Color.black);
                            out6.setForeground(Color.black);


                            out1.setText(multipliedList.get(0) + " " + valuesEnergy[0]);
                            out2.setText(multipliedList.get(1) + " " + valuesEnergy[1]);
                            out3.setText(multipliedList.get(2) + " " + valuesEnergy[2]);
                            out4.setText(multipliedList.get(3) + " " + valuesEnergy[3]);
                            out5.setText(multipliedList.get(4) + " " + valuesEnergy[4]);
                            out6.setText(multipliedList.get(5) + " " + valuesEnergy[5]);
                            multipliedList.removeIf(n -> n < 1);
                        }
                        case "MJ" -> {

                            for (Number n : energyListMJ) {
                                multipliedList.add(n.doubleValue() * inin);
                            }

                            out1.setText("");
                            out2.setText("");
                            out3.setText("");
                            out4.setText("");
                            out5.setText("");
                            out6.setText("");
                            out7.setText("");
                            out8.setText("");

                            out1.setForeground(Color.black);
                            out2.setForeground(Color.black);
                            out3.setForeground(Color.red);
                            out4.setForeground(Color.black);
                            out5.setForeground(Color.black);
                            out6.setForeground(Color.black);


                            out1.setText(multipliedList.get(0) + " " + valuesEnergy[0]);
                            out2.setText(multipliedList.get(1) + " " + valuesEnergy[1]);
                            out3.setText(multipliedList.get(2) + " " + valuesEnergy[2]);
                            out4.setText(multipliedList.get(3) + " " + valuesEnergy[3]);
                            out5.setText(multipliedList.get(4) + " " + valuesEnergy[4]);
                            out6.setText(multipliedList.get(5) + " " + valuesEnergy[5]);
                            multipliedList.removeIf(n -> n < 1);
                        }
                        case "Wh" -> {

                            for (Number n : energyListWh) {
                                multipliedList.add(n.doubleValue() * inin);
                            }

                            out1.setText("");
                            out2.setText("");
                            out3.setText("");
                            out4.setText("");
                            out5.setText("");
                            out6.setText("");
                            out7.setText("");
                            out8.setText("");

                            out1.setForeground(Color.black);
                            out2.setForeground(Color.black);
                            out3.setForeground(Color.black);
                            out4.setForeground(Color.red);
                            out5.setForeground(Color.black);
                            out6.setForeground(Color.black);


                            out1.setText(multipliedList.get(0) + " " + valuesEnergy[0]);
                            out2.setText(multipliedList.get(1) + " " + valuesEnergy[1]);
                            out3.setText(multipliedList.get(2) + " " + valuesEnergy[2]);
                            out4.setText(multipliedList.get(3) + " " + valuesEnergy[3]);
                            out5.setText(multipliedList.get(4) + " " + valuesEnergy[4]);
                            out6.setText(multipliedList.get(5) + " " + valuesEnergy[5]);
                            multipliedList.removeIf(n -> n < 1);
                        }
                        case "kWh" -> {

                            for (Number n : energyListkWh) {
                                multipliedList.add(n.doubleValue() * inin);
                            }

                            out1.setText("");
                            out2.setText("");
                            out3.setText("");
                            out4.setText("");
                            out5.setText("");
                            out6.setText("");
                            out7.setText("");
                            out8.setText("");

                            out1.setForeground(Color.black);
                            out2.setForeground(Color.black);
                            out3.setForeground(Color.black);
                            out4.setForeground(Color.black);
                            out5.setForeground(Color.red);
                            out6.setForeground(Color.black);


                            out1.setText(multipliedList.get(0) + " " + valuesEnergy[0]);
                            out2.setText(multipliedList.get(1) + " " + valuesEnergy[1]);
                            out3.setText(multipliedList.get(2) + " " + valuesEnergy[2]);
                            out4.setText(multipliedList.get(3) + " " + valuesEnergy[3]);
                            out5.setText(multipliedList.get(4) + " " + valuesEnergy[4]);
                            out6.setText(multipliedList.get(5) + " " + valuesEnergy[5]);
                            multipliedList.removeIf(n -> n < 1);
                        }
                        case "MWh" -> {

                            for (Number n : energyListMWh) {
                                multipliedList.add(n.doubleValue() * inin);
                            }

                            out1.setText("");
                            out2.setText("");
                            out3.setText("");
                            out4.setText("");
                            out5.setText("");
                            out6.setText("");
                            out7.setText("");
                            out8.setText("");

                            out1.setForeground(Color.black);
                            out2.setForeground(Color.black);
                            out3.setForeground(Color.black);
                            out4.setForeground(Color.black);
                            out5.setForeground(Color.black);
                            out6.setForeground(Color.red);


                            out1.setText(multipliedList.get(0) + " " + valuesEnergy[0]);
                            out2.setText(multipliedList.get(1) + " " + valuesEnergy[1]);
                            out3.setText(multipliedList.get(2) + " " + valuesEnergy[2]);
                            out4.setText(multipliedList.get(3) + " " + valuesEnergy[3]);
                            out5.setText(multipliedList.get(4) + " " + valuesEnergy[4]);
                            out6.setText(multipliedList.get(5) + " " + valuesEnergy[5]);
                            multipliedList.removeIf(n -> n < 1);
                        }
                    }
                }
                if (selection1.equals("Temperature")) {
                    switch (selection2) {
                        case "°C" -> {

                            for (Number n : tempListC) {
                                multipliedList.add(n.doubleValue() * inin);
                            }

                            out1.setText("");
                            out2.setText("");
                            out3.setText("");
                            out4.setText("");
                            out5.setText("");
                            out6.setText("");
                            out7.setText("");
                            out8.setText("");

                            out1.setForeground(Color.red);
                            out2.setForeground(Color.black);
                            out3.setForeground(Color.black);

                            out1.setText(multipliedList.get(0) + " " + valuesTemp[0]);
                            out2.setText(multipliedList.get(1) + " " + valuesTemp[1]);
                            out3.setText(multipliedList.get(2) + " " + valuesTemp[2]);
                            multipliedList.removeIf(n -> n < 1);
                        }
                        case "°F" -> {

                            for (Number n : tempListF) {
                                multipliedList.add(n.doubleValue() * inin);
                            }

                            out1.setText("");
                            out2.setText("");
                            out3.setText("");
                            out4.setText("");
                            out5.setText("");
                            out6.setText("");
                            out7.setText("");
                            out8.setText("");

                            out1.setForeground(Color.black);
                            out2.setForeground(Color.red);
                            out3.setForeground(Color.black);

                            out1.setText(multipliedList.get(0) + " " + valuesTemp[0]);
                            out2.setText(multipliedList.get(1) + " " + valuesTemp[1]);
                            out3.setText(multipliedList.get(2) + " " + valuesTemp[2]);
                            multipliedList.removeIf(n -> n < 1);
                        }
                        case "°K" -> {

                            for (Number n : tempListK) {
                                multipliedList.add(n.doubleValue() * inin);
                            }

                            out1.setText("");
                            out2.setText("");
                            out3.setText("");
                            out4.setText("");
                            out5.setText("");
                            out6.setText("");
                            out7.setText("");
                            out8.setText("");

                            out1.setForeground(Color.black);
                            out2.setForeground(Color.black);
                            out3.setForeground(Color.red);

                            out1.setText(multipliedList.get(0) + " " + valuesTemp[0]);
                            out2.setText(multipliedList.get(1) + " " + valuesTemp[1]);
                            out3.setText(multipliedList.get(2) + " " + valuesTemp[2]);
                            multipliedList.removeIf(n -> n < 1);
                        }
                    }
                }
                if (selection1.equals("Pressure")) {
                    switch (selection2) {
                        case "bar" -> {

                            for (Number n : pressListBar) {
                                multipliedList.add(n.doubleValue() * inin);
                            }

                            out1.setText("");
                            out2.setText("");
                            out3.setText("");
                            out4.setText("");
                            out5.setText("");
                            out6.setText("");
                            out7.setText("");
                            out8.setText("");

                            out1.setForeground(Color.red);
                            out2.setForeground(Color.black);
                            out3.setForeground(Color.black);
                            out4.setForeground(Color.black);
                            out5.setForeground(Color.black);

                            out1.setText(multipliedList.get(0) + " " + valuesPress[0]);
                            out2.setText(multipliedList.get(1) + " " + valuesPress[1]);
                            out3.setText(multipliedList.get(2) + " " + valuesPress[2]);
                            out4.setText(multipliedList.get(3) + " " + valuesPress[3]);
                            out5.setText(multipliedList.get(4) + " " + valuesPress[4]);
                            multipliedList.removeIf(n -> n < 1);
                        }
                        case "Pa" -> {

                            for (Number n : pressListPa) {
                                multipliedList.add(n.doubleValue() * inin);
                            }

                            out1.setText("");
                            out2.setText("");
                            out3.setText("");
                            out4.setText("");
                            out5.setText("");
                            out6.setText("");
                            out7.setText("");
                            out8.setText("");

                            out1.setForeground(Color.black);
                            out2.setForeground(Color.red);
                            out3.setForeground(Color.black);
                            out4.setForeground(Color.black);
                            out5.setForeground(Color.black);

                            out1.setText(multipliedList.get(0) + " " + valuesPress[0]);
                            out2.setText(multipliedList.get(1) + " " + valuesPress[1]);
                            out3.setText(multipliedList.get(2) + " " + valuesPress[2]);
                            out4.setText(multipliedList.get(3) + " " + valuesPress[3]);
                            out5.setText(multipliedList.get(4) + " " + valuesPress[4]);
                            multipliedList.removeIf(n -> n < 1);
                        }
                        case "kPa" -> {

                            for (Number n : pressListkPa) {
                                multipliedList.add(n.doubleValue() * inin);
                            }

                            out1.setText("");
                            out2.setText("");
                            out3.setText("");
                            out4.setText("");
                            out5.setText("");
                            out6.setText("");
                            out7.setText("");
                            out8.setText("");

                            out1.setForeground(Color.black);
                            out2.setForeground(Color.black);
                            out3.setForeground(Color.red);
                            out4.setForeground(Color.black);
                            out5.setForeground(Color.black);

                            out1.setText(multipliedList.get(0) + " " + valuesPress[0]);
                            out2.setText(multipliedList.get(1) + " " + valuesPress[1]);
                            out3.setText(multipliedList.get(2) + " " + valuesPress[2]);
                            out4.setText(multipliedList.get(3) + " " + valuesPress[3]);
                            out5.setText(multipliedList.get(4) + " " + valuesPress[4]);
                            multipliedList.removeIf(n -> n < 1);
                        }
                        case "MPa" -> {

                            for (Number n : pressListMPa) {
                                multipliedList.add(n.doubleValue() * inin);
                            }

                            out1.setText("");
                            out2.setText("");
                            out3.setText("");
                            out4.setText("");
                            out5.setText("");
                            out6.setText("");
                            out7.setText("");
                            out8.setText("");

                            out1.setForeground(Color.black);
                            out2.setForeground(Color.black);
                            out3.setForeground(Color.black);
                            out4.setForeground(Color.red);
                            out5.setForeground(Color.black);

                            out1.setText(multipliedList.get(0) + " " + valuesPress[0]);
                            out2.setText(multipliedList.get(1) + " " + valuesPress[1]);
                            out3.setText(multipliedList.get(2) + " " + valuesPress[2]);
                            out4.setText(multipliedList.get(3) + " " + valuesPress[3]);
                            out5.setText(multipliedList.get(4) + " " + valuesPress[4]);
                            multipliedList.removeIf(n -> n < 1);
                        }
                        case "psi" -> {

                            for (Number n : pressListPsi) {
                                multipliedList.add(n.doubleValue() * inin);
                            }

                            out1.setText("");
                            out2.setText("");
                            out3.setText("");
                            out4.setText("");
                            out5.setText("");
                            out6.setText("");
                            out7.setText("");
                            out8.setText("");

                            out1.setForeground(Color.black);
                            out2.setForeground(Color.black);
                            out3.setForeground(Color.black);
                            out4.setForeground(Color.black);
                            out5.setForeground(Color.red);

                            out1.setText(multipliedList.get(0) + " " + valuesPress[0]);
                            out2.setText(multipliedList.get(1) + " " + valuesPress[1]);
                            out3.setText(multipliedList.get(2) + " " + valuesPress[2]);
                            out4.setText(multipliedList.get(3) + " " + valuesPress[3]);
                            out5.setText(multipliedList.get(4) + " " + valuesPress[4]);
                            multipliedList.removeIf(n -> n < 1);
                        }
                    }
                }


            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input");
            }
        });


        txt1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);

                txt1.setEditable(e.getKeyChar() >= '0' && e.getKeyChar() <= '9' || (e.getKeyChar() == ',') ||
                        (e.getKeyChar() == KeyEvent.VK_BACK_SPACE) || (e.getKeyChar() == KeyEvent.VK_DELETE));

                String userInput = txt1.getText();
                if (userInput.endsWith(",") || (userInput.contains(",")) || (userInput.isEmpty())) {
                    e.consume();
                    txt1.setEditable(!(e.getKeyChar() == ','));
                }
            }
        });
    }



    public static void main(String[] args) {
        JFrame frame = new JFrame("Convertor");
        frame.setContentPane(new Convertor().ConvertorPan);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }
}