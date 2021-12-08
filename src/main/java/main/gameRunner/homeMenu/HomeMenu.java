package main.gameRunner.homeMenu;

/*
 *  Brick Destroy - A simple Arcade video game
 *   Copyright (C) 2021  Loo Xuen Yi
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import main.gameRunner.gameBoard.GameFrame;
import main.gameRunner.instructionPage.InstructionPage;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

/**
 * This class specifies the properties of the Home Menu, activated upon running, and "exit" from the pause menu
 *
 * Refactored by:
 * @author LooXuenYi
 */
public class HomeMenu extends Image implements MouseListener, MouseMotionListener {

    // Variables Declaration
    private static final String GREETINGS = "Welcome to:";
    private static final String GAME_TITLE = "Brick Destroy";
    private static final String CREDITS = "Version 0.1.1";
    private static final String START_TEXT = "Start";
    private static final String EXIT_TEXT = "Exit";
    private static final String INSTRUCTION_TEXT = "Info";
    private static final Color BG_COLOR = Color.GREEN.darker();
    private static final Color BORDER_COLOR = new Color(200,8,21); //Venetian Red
    private static final Color DASH_BORDER_COLOR = new  Color(255, 216, 0);//school bus yellow
    private static final Color TEXT_COLOR = new Color(0, 0, 0);//pure black
    private static final Color CLICKED_BUTTON_COLOR = BG_COLOR.brighter();
    private static final Color CLICKED_TEXT = Color.WHITE;
    private static final int BORDER_SIZE = 5;
    private static final float[] DASHES = {12,6};
    private Rectangle menuFace;
    private Rectangle startButton;
    private Rectangle exitButton;
    private Rectangle instructionButton;
    private BasicStroke borderStoke;
    private BasicStroke borderStoke_noDashes;
    private Font greetingsFont;
    private Font gameTitleFont;
    private Font creditsFont;
    private Font buttonFont;
    private GameFrame owner;
    private InstructionPage instruction;
    private boolean startClicked;
    private boolean menuClicked;
    private boolean instructionClicked;
    private boolean drawImage = true;

    /**
     * This constructor includes all the properties that are needed to make the home menu function
     *
     * @param owner
     * @param area
     */
    public HomeMenu(GameFrame owner,Dimension area)
    {
        super();
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.owner = owner;

        menuFace = new Rectangle(new Point(0,0),area);
        this.setPreferredSize(area);

        Dimension btnDim = new Dimension(area.width / 3, area.height / 12);
        startButton = new Rectangle(btnDim);
        exitButton = new Rectangle(btnDim);
        instructionButton = new Rectangle(btnDim);

        borderStoke = new BasicStroke(BORDER_SIZE,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND,0,DASHES,0);
        borderStoke_noDashes = new BasicStroke(BORDER_SIZE,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);

        greetingsFont = new Font("Noto Mono",Font.PLAIN,25);
        gameTitleFont = new Font("Noto Mono",Font.BOLD,40);
        creditsFont = new Font("Monospaced",Font.PLAIN,10);
        buttonFont = new Font("Monospaced",Font.PLAIN,startButton.height-2);
    }

    /**
     * This method allows the home menu to be rendered, and includes a new background picture
     *
     * @param g
     */
    public void paint(Graphics g)
    {
        super.paint(g);
        drawMenu((Graphics2D)g);
    }

    /**
     * This method renders the home menu
     *
     * @param g2d
     */
    public void drawMenu(Graphics2D g2d)
    {
        /*
        all the following method calls need a relative
        painting directly into the HomeMenu rectangle,
        so the translation is made here so the other methods do not do that.
         */
        Color prevColor = g2d.getColor();
        Font prevFont = g2d.getFont();

        double x = menuFace.getX();
        double y = menuFace.getY();

        g2d.translate(x,y);

        drawText(g2d);
        drawButton(g2d);

        g2d.translate(-x,-y);
        g2d.setFont(prevFont);
        g2d.setColor(prevColor);
    }

    /**
     * This method defines the container that holds the content of the home menu
     *
     * @param g2d
     */
    private void drawContainer(Graphics2D g2d)
    {
        Color prev = g2d.getColor();

        g2d.setColor(BG_COLOR);
        g2d.fill(menuFace);

        Stroke tmp = g2d.getStroke();

        g2d.setStroke(borderStoke_noDashes);
        g2d.setColor(DASH_BORDER_COLOR);
        g2d.draw(menuFace);

        g2d.setStroke(borderStoke);
        g2d.setColor(BORDER_COLOR);
        g2d.draw(menuFace);

        g2d.setStroke(tmp);

        g2d.setColor(prev);
    }

    /**
     * This method renders the texts on the home menu
     *
     * @param g2d
     */
    private void drawText(Graphics2D g2d)
    {
        g2d.setColor(TEXT_COLOR);

        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D greetingsRect = greetingsFont.getStringBounds(GREETINGS,frc);
        Rectangle2D gameTitleRect = gameTitleFont.getStringBounds(GAME_TITLE,frc);
        Rectangle2D creditsRect = creditsFont.getStringBounds(CREDITS,frc);

        int sX,sY;

        sX = (int)(menuFace.getWidth() - greetingsRect.getWidth()) / 2;
        sY = (int)(menuFace.getHeight() / 4);

        g2d.setFont(greetingsFont);
        g2d.drawString(GREETINGS,sX,sY);

        sX = (int)(menuFace.getWidth() - gameTitleRect.getWidth()) / 2;
        sY += (int) gameTitleRect.getHeight() * 1.1;//add 10% of String height between the two strings

        g2d.setFont(gameTitleFont);
        g2d.drawString(GAME_TITLE,sX,sY);

        sX = (int)(menuFace.getWidth() - creditsRect.getWidth()) / 2;
        sY += (int) creditsRect.getHeight() * 1.1;

        g2d.setFont(creditsFont);
        g2d.drawString(CREDITS,sX,sY);
    }

    /**
     * This method renders the shape and size of the button on the home menu
     *
     * @param g2d
     */
    private void drawButton(Graphics2D g2d)
    {
        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D txtRect = buttonFont.getStringBounds(START_TEXT,frc);
        Rectangle2D mTxtRect = buttonFont.getStringBounds(EXIT_TEXT,frc);
        Rectangle2D iTxtRect = buttonFont.getStringBounds(INSTRUCTION_TEXT,frc);

        g2d.setFont(buttonFont);

        int x = (menuFace.width - startButton.width) / 2;
        int y =(int) ((menuFace.height - startButton.height) * 0.8);

        //Start Button Placement
        startButton.setLocation(x,y);

        x = (int)(startButton.getWidth() - txtRect.getWidth()) / 2;
        y = (int)(startButton.getHeight() - txtRect.getHeight()) / 2;

        x += startButton.x;
        y += startButton.y + (startButton.height * 0.9);

        if(startClicked){
            Color tmp = g2d.getColor();
            g2d.setColor(CLICKED_BUTTON_COLOR);
            g2d.draw(startButton);
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(START_TEXT,x,y);
            g2d.setColor(tmp);
        }
        else{
            g2d.draw(startButton);
            g2d.drawString(START_TEXT,x,y);
        }

        x = startButton.x;
        y = startButton.y;

        y *= 1.2;

        //exit button placement
        exitButton.setLocation(x,y);

        x = (int)(exitButton.getWidth() - mTxtRect.getWidth()) / 2;
        y = (int)(exitButton.getHeight() - mTxtRect.getHeight()) / 2;

        x += exitButton.x;
        y += exitButton.y + (startButton.height * 0.9);

        if(menuClicked){
            Color tmp = g2d.getColor();

            g2d.setColor(CLICKED_BUTTON_COLOR);
            g2d.draw(exitButton);
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(EXIT_TEXT,x,y);
            g2d.setColor(tmp);
        }
        else{
            g2d.draw(exitButton);
            g2d.drawString(EXIT_TEXT,x,y);
        }

        y *= 0.6;

        //instruction button placement
        instructionButton.setLocation(x,y);

        x = (int)(instructionButton.getWidth() - iTxtRect.getWidth()) / 2;
        y = (int)(instructionButton.getHeight() - iTxtRect.getHeight()) / 2;

        x += instructionButton.x;
        y += instructionButton.y + (startButton.height * 0.9);

        if(instructionClicked){
            Color tmp = g2d.getColor();

            g2d.setColor(CLICKED_BUTTON_COLOR);
            g2d.draw(instructionButton);
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(INSTRUCTION_TEXT,x,y);
            g2d.setColor(tmp);
        }
        else{
            g2d.draw(instructionButton);
            g2d.drawString(INSTRUCTION_TEXT,x,y);
        }

    }

    /**
     * This method checks of the mouse is clicked on a button
     *
     * @param mouseEvent
     */
    @Override
    public void mouseClicked(MouseEvent mouseEvent)
    {
        Point p = mouseEvent.getPoint();
        if(startButton.contains(p)){
           owner.enableGameBoard();
        }
        else if(exitButton.contains(p)){
            System.out.println("Goodbye " + System.getProperty("user.name"));
            System.exit(0);
        }
        else if(instructionButton.contains(p)){

        }

    }

    /**
     * This method checks if a button on the mouse is pressed but not released
     *
     * @param mouseEvent
     */
    @Override
    public void mousePressed(MouseEvent mouseEvent)
    {
        Point p = mouseEvent.getPoint();
        if(startButton.contains(p)){
            startClicked = true;
            repaint(startButton.x,startButton.y,startButton.width+1,startButton.height+1);

        }
        else if(exitButton.contains(p)){
            menuClicked = true;
            repaint(exitButton.x, exitButton.y, exitButton.width+1, exitButton.height+1);
        }
    }

    /**
     * This method checks if a pressed button is released on the mouse
     *
     * @param mouseEvent
     */
    @Override
    public void mouseReleased(MouseEvent mouseEvent)
    {
        if(startClicked ){
            startClicked = false;
            repaint(startButton.x,startButton.y,startButton.width+1,startButton.height+1);
        }
        else if(menuClicked){
            menuClicked = false;
            repaint(exitButton.x, exitButton.y, exitButton.width+1, exitButton.height+1);
        }
    }

    /**
     * This method checks if a pointer enters the window of the home menu
     *
     * @param mouseEvent
     */
    @Override
    public void mouseEntered(MouseEvent mouseEvent) { }

    /**
     * This method checks if the pointer has exited the window of the home menu
     *
     * @param mouseEvent
     */
    @Override
    public void mouseExited(MouseEvent mouseEvent) { }

    /**
     * This method checks if the pointer is dragging (the window)
     *
     * @param mouseEvent
     */
    @Override
    public void mouseDragged(MouseEvent mouseEvent) { }

    /**
     * This method checks if the pointer is moving in the window
     * @param mouseEvent
     */
    @Override
    public void mouseMoved(MouseEvent mouseEvent)
    {
        Point p = mouseEvent.getPoint();
        if(startButton.contains(p) || exitButton.contains(p))
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        else
            this.setCursor(Cursor.getDefaultCursor());
    }

}
