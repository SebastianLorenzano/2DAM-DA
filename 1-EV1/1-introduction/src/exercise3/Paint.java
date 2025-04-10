package exercise3;

public class Paint extends Artwork
{
    private MaterialType material;
    private StyleType style;

    public MaterialType getMaterial() {
        return material;
    }

    public void setMaterial(MaterialType material) {
        this.material = material;
    }

    public StyleType getStyle() {
        return style;
    }

    public void setStyle(StyleType style) {
        this.style = style;
    }
}
