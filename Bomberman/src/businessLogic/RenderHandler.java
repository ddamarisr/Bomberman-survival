/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

/**
 *
 * @author danie
 */
import data.Rectangle;
import data.Sprite;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class RenderHandler {

    private final BufferedImage view;
    private final int[] pixels;
    private final int width;
    private final int height;
    private final Rectangle camera;
    public static int xZoom = 3;
    public static int yZoom = 3;

    public RenderHandler(int width, int height) {

        this.width = width;
        this.height = height;

        //Create a BufferedImage that will represent our view.
        view = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        //Create an array for the pixels
        pixels = ((DataBufferInt) view.getRaster().getDataBuffer()).getData();

        camera = new Rectangle(width / 2, height / 2, width, height);

        camera.x = 0;
        camera.y = 0;
    }

    //Render our image to our array of pixels
    public void render(Graphics graphics) {
        graphics.drawImage(view, 0, 0, view.getWidth(), view.getHeight(), null);

    }

    public void renderImage(BufferedImage image, int xPos, int yPos, int xZoom, int yZoom) {
        int[] imagePixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
        renderArray(imagePixels, xPos, yPos, xZoom, yZoom, image.getWidth(), image.getHeight());

    }

    public void renderArray(int[] renderPixels, int xPos, int yPos, int xZoom, int yZoom, int renderWidth, int renderHeight) {
        for (int y = 0; y < renderHeight; y++) {
            for (int x = 0; x < renderWidth; x++) {
                for (int yZoomPosition = 0; yZoomPosition < yZoom; yZoomPosition++) {
                    for (int xZoomPosition = 0; xZoomPosition < xZoom; xZoomPosition++) {
                        setPixel(renderPixels[x + y * renderWidth], (x * xZoom) + xPos + xZoomPosition, (y * yZoom) + yPos + yZoomPosition);
                    }
                }

            }
        }

    }

    private void setPixel(int pixel, int x, int y) {
        if (x >= camera.x && y >= camera.y && x <= camera.x + camera.w && y <= camera.y + camera.h) {
            int pixelIndex = (x - camera.x) + (y - camera.y) * view.getWidth();
            if (pixels.length > pixelIndex && pixel != Game.alpha) {
                pixels[pixelIndex] = pixel;
            }

        }
    }

    public void renderRectangle(Rectangle rectangle, int xZoom, int yZoom) {
        int[] rectanglePixels = rectangle.getPixels();
        if (rectanglePixels != null) {
            renderArray(rectanglePixels, rectangle.x, rectangle.y, xZoom, yZoom, rectangle.w, rectangle.h);

        }

    }

    public void renderSprite(Sprite sprite, int xPos, int yPos, int xZoom, int yZoom) {
        renderArray(sprite.getPixels(), xPos, yPos, xZoom, yZoom, sprite.getWidth(), sprite.getHeight());
    }

    public Rectangle getCamera() {
        return camera;
    }

    public void clear() {

        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = 0;
        }
    }

}
