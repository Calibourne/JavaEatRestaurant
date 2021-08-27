package View;

import Model.Dish;
import Model.Restaurant;
import Utils.DocxWriter;

import java.io.IOException;
import java.util.List;

public class Launcher {
    public static void main(String[] args) {
        // See https://stackoverflow.com/a/52654791/3956070 for explanation
        try{
            CSV_Main.main(args);
        }catch (IOException e){
            e.getMessage();
        }
        Main.main(args);
        List<Dish> ts = Restaurant.getInstance().getProfitRelation().stream().toList();
        String[] prompt = new String[ts.size()+1];
        prompt[0] = "SUBTITLE-CENTER~(Dish price / minute of preparation)";
        for (int i = 1; i < prompt.length-1; i++) {
            prompt[i] = String.format("%s profit: %.2f ₪/min",ts.get(i).getDishName(),ts.get(i-1).getPrice() / (1.0 *ts.get(i-1).getTimeToMake()));
            if(i > 0) {
                if (i == 1) {
                    prompt[1] = "🥇 " + prompt[1];
                } else if (i == 2) {
                    prompt[2] = "🥈 " + prompt[2];
                } else if (i == 3) {
                    prompt[3] = "🥉 " + prompt[3];
                } else if (i > 3) {
                    prompt[i] = i + ") " + prompt[i];
                }
                prompt[i] = "-CENTER~" + prompt[i];
            }
        }
        DocxWriter.getInstance().writeToDocx("profits.docx", "JavaEat Profits List", prompt);
    }
}
