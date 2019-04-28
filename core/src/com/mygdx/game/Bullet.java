package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import static com.badlogic.gdx.math.MathUtils.floor;

public class Bullet {
    static final float radius = 15F;
    Vector2 bulletPosition;
    Vector2 bulletVelocity;
    WaitState ws;
    boolean started=false;
    ShapeRenderer shapeRenderer=new ShapeRenderer();
    public Bullet(WaitState ws){this.ws=ws;}

    public Bullet(WaitState ws,Vector2 start, Vector2 velocity){
        this(ws);
        set(start,velocity);
    }

    public void set(Vector2 pos, Vector2 velocity){
        bulletPosition=new Vector2(pos.x,pos.y);
        bulletVelocity=new Vector2(velocity.x, velocity.y);
        bulletVelocity.clamp(550,550);
    }

    public void faster(){bulletVelocity.clamp(1000,1000);}

    public int update(float gameLoopTime) {
        if(bulletPosition.y<radius/2 && started==true)
            return 0;
        if(bulletPosition.y>radius/2)
        started=true;
       // System.out.println(bulletPosition);
        bulletPosition.mulAdd(bulletVelocity,gameLoopTime);
        if(bulletPosition.y<0)
            return 1;
        if(bulletPosition.x-radius<0 || bulletPosition.x>= Gdx.graphics.getWidth()-radius )
            bulletVelocity.x=-bulletVelocity.x;
        if(bulletPosition.y>=Gdx.graphics.getHeight()-radius)
            bulletVelocity.y=-bulletVelocity.y;

        float width=ws.allBlocksWidth/5;
        float height=ws.allBlocksHeight/5;

                    int i=floor((bulletPosition.x+radius-ws.allBlocksX)/width);
                    int j=floor((bulletPosition.y+radius-ws.allBlocksY)/height);
                    if(i>=0 && i<5 && j>=0 && j<5 && ws.arrOfBlocks[i][j].value > 0) {
                        ws.arrOfBlocks[i][j].decrease();
                        // System.out.println(bulletPosition+" "+i+" "+j);
                        if (bulletPosition.x + radius - 10 < ws.allBlocksX + i * width || bulletPosition.x + radius + 10 > ws.allBlocksX + (i + 1) * width)
                            bulletVelocity.x = -bulletVelocity.x;
                        if (bulletPosition.y + radius - 10 < ws.allBlocksY + j * height || bulletPosition.y + radius + 10 > ws.allBlocksY + (j + 1) * height)
                            bulletVelocity.y = -bulletVelocity.y;
                    }
                    else {

                        i = floor((bulletPosition.x - radius - ws.allBlocksX) / width);
                        j = floor((bulletPosition.y - radius - ws.allBlocksY) / height);
                        if (i >= 0 && i < 5 && j >= 0 && j < 5 && ws.arrOfBlocks[i][j].value > 0) {
                            ws.arrOfBlocks[i][j].decrease();
                            // System.out.println(bulletPosition+" "+i+" "+j);
                            if (bulletPosition.x + radius - 10 < ws.allBlocksX + i * width || bulletPosition.x + radius + 10 > ws.allBlocksX + (i + 1) * width)
                                bulletVelocity.x = -bulletVelocity.x;
                            if (bulletPosition.y + radius - 10 < ws.allBlocksY + j * height || bulletPosition.y + radius + 10 > ws.allBlocksY + (j + 1) * height)
                                bulletVelocity.y = -bulletVelocity.y;
                        }
                    }

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1,1,1,0);
        shapeRenderer.circle(bulletPosition.x,bulletPosition.y,radius);
        shapeRenderer.end();
        return 1;
        }

}
