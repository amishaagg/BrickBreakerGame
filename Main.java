import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        JFrame jFrame = new JFrame();
        GamePlay gp = new GamePlay();
        jFrame.setBounds(10,10,700,600);
        jFrame.setResizable(false);
        jFrame.setTitle("Best Brick Breaker Game");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //to exit when user clicks cross
        jFrame.setVisible(true);
        jFrame.add(gp);

    }
}