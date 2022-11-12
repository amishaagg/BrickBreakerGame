import java.awt.*;

public class GenerateMap {
    //add bricks

    int[][] map; //map for all bricks
    int brickWidth; //brick width
    int brickHeight; //brick Height

    GenerateMap(int rows,int cols){
        //Map has rows*cols number of bricks
        map = new int[rows][cols];
        for(int i=0;i<map.length;i++){
            for(int j=0;j<map[0].length;j++){
                map[i][j] = 1; //brick hasn't been hit
            }
        }
        brickHeight = 150/rows;
        brickWidth = 540/cols;
    }

    public void makeBricks(Graphics2D g){
        //generate the bricks
        for(int i=0;i<map.length;i++){
            for(int j=0;j<map[0].length;j++){
                if(map[i][j]>0) { //only if brick hasn't been hit
                    g.setColor(Color.red);
                    //solid red brick
                    g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                    g.setStroke(new BasicStroke(3.0f)); //border of 3.0 width
                    g.setColor(Color.black);
                    g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                }
            }
        }
    }

    public void setValue(int i,int j,int val){
        map[i][j] = val;
    }
}
