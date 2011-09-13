package info.apoluna.vector;

import java.awt.Color;
import java.util.Observable;

public class DrawConfig extends Observable {
	private static DrawConfig instance = new DrawConfig();
	private boolean mesh = false;
	private boolean contour = false;
	private boolean vector = true;
	private boolean boundary = true;
	private boolean elementContour = false;

	private float contourLineWidth = 0.00001f;
	private float vectorLineWidth = 0.00001f;
	private float meshLineWidth = 0.00001f;

	private Color vectorColor = Color.red;
	private Color meshColor = Color.black;
	private Color contourColor = Color.black;

	private double zoom = 1.0;
	private double vecterMagnification = 0.03;
	private double vectorArrowLength = 0.001;
	private double vectorArrowAngle = 0.5;
	private int contourLines = 40;
	private double contourMinimum = -0.001;
	private double contourMaximum = 0.001;
	private boolean colorContour = true;
	private boolean drawColor = true;

	public double getZoom() {
		return zoom;
	}

	public boolean isBoundary() {
		return boundary;
	}

	public void setBoundary(boolean boundary) {
		this.boundary = boundary;
	}

	public double getVecterMagnification() {
		return vecterMagnification;
	}

	public void setVecterMagnification(double vecterMagnification) {
		this.vecterMagnification = vecterMagnification;
	}

	public double getVectorArrowLength() {
		return vectorArrowLength;
	}

	public void setVectorArrowLength(double vectorArrowLength) {
		this.vectorArrowLength = vectorArrowLength;
	}

	public double getVectorArrowAngle() {
		return vectorArrowAngle;
	}

	public void setVectorArrowAngle(double vectorArrowAngle) {
		this.vectorArrowAngle = vectorArrowAngle;
	}

	public void setZoom(double zoom) {
		this.zoom = zoom;
		notifyObservers(zoom);
	}

	public static DrawConfig getInstance() {
		return instance;
	}

	public boolean isMesh() {
		return mesh;
	}

	public void setMesh(boolean mesh) {
		this.mesh = mesh;
	}

	public boolean isContour() {
		return contour;
	}

	public void setContour(boolean contour) {
		this.contour = contour;
	}

	public boolean isVector() {
		return vector;
	}

	public void setVector(boolean vector) {
		this.vector = vector;
	}

	public static void setInstance(DrawConfig instance) {
		DrawConfig.instance = instance;
	}

	public void notifyObservers(Object arg) {
		setChanged();

		super.notifyObservers(arg);

		clearChanged();
	}

	public int getContourLines() {
		return contourLines;
	}

	public void setContourLines(int contourLines) {
		this.contourLines = contourLines;
	}

	public double getContourMinimum() {
		return contourMinimum;
	}

	public void setContourMinimum(double contourMinimum) {
		this.contourMinimum = contourMinimum;
	}

	public double getContourMaximum() {
		return contourMaximum;
	}

	public void setContourMaximum(double contourMaximum) {
		this.contourMaximum = contourMaximum;
	}

	public boolean isColorContour() {
		return colorContour;
	}

	public void setColorContour(boolean contourColor) {
		this.colorContour = contourColor;
	}

	public boolean isDrawColor() {
		return drawColor;
	}

	public void setDrawColor(boolean drawColor) {
		this.drawColor = drawColor;
	}

	public float getContourLineWidth() {
		return contourLineWidth;
	}

	public void setContourLineWidth(float contourLineWidth) {
		this.contourLineWidth = contourLineWidth;
	}

	public float getVectorLineWidth() {
		return vectorLineWidth;
	}

	public void setVectorLineWidth(float vectorLineWidth) {
		this.vectorLineWidth = vectorLineWidth;
	}

	public float getMeshLineWidth() {
		return meshLineWidth;
	}

	public void setMeshLineWidth(float meshLineWidth) {
		this.meshLineWidth = meshLineWidth;
	}

	public Color getVectorColor() {
		return vectorColor;
	}

	public void setVectorColor(Color vectorColor) {
		this.vectorColor = vectorColor;
	}

	public Color getMeshColor() {
		return meshColor;
	}

	public void setMeshColor(Color meshColor) {
		this.meshColor = meshColor;
	}

	public Color getContourColor() {
		return contourColor;
	}

	public void setContourColor(Color contourColor) {
		this.contourColor = contourColor;
	}

	public boolean isElementContour() {
		return elementContour;
	}

	public void setElementContour(boolean elementContour) {
		this.elementContour = elementContour;
	}
}
