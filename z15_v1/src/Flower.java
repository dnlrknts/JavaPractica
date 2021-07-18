class VisualParameters
{
    public static final String VISUAL_PARAMETERS = "visualParameters";
    public static final String STEM_COLOR = "stemColor";
    public static final String LEAF_COLOR = "leafColor";
    public static final String AVERAGE_SIZE = "averageSize";

    public VisualParameters(){}
    private String stemColor;
    private String leafColor;
    private double averageSize;

    public void setStemColor(String steamColor)
    {
        this.stemColor = steamColor;
    }

    public void setLeafColor(String leafColor)
    {
        this.leafColor = leafColor;
    }

    public void setAverageSize(double averageSize)
    {
        this.averageSize = averageSize;
    }

    @Override
    public String toString()
    {
        return "VisualParameters{" +
                "stemColor='" + stemColor + '\'' +
                ", leafColor='" + leafColor + '\'' +
                ", averageSize=" + averageSize +
                '}';
    }
}

class GrowingTips
{
    public static final String GROWING_TIPS = "growingTips";
    public static final String TEMPERATURE = "temperature";
    public static final String LIGHT_LOVING = "light-loving";
    public static final String WATERING = "watering";
    public static final String MULTIPLYING = "multiplying";
    public static final String YES = "да";
    public GrowingTips(){}
    private double temperature;
    private boolean lightLoving;
    private double watering;
    private String multiplying;

    public void setTemperature(double temperature)
    {
        this.temperature = temperature;
    }

    public void setLightLoving(boolean lightLoving)
    {
        this.lightLoving = lightLoving;
    }

    public void setWatering(double watering)
    {
        this.watering = watering;
    }

    public void setMultiplying(String multiplying)
    {
        this.multiplying = multiplying;
    }

    @Override
    public String toString()
    {
        return "GrowingTips{" +
                "temperature=" + temperature +
                ", lightLoving=" + lightLoving +
                ", watering=" + watering +
                ", multiplying='" + multiplying + '\'' +
                '}';
    }
}
public class Flower
{
    public static final String FLOWER = "flower";
    public static final String NAME = "name";
    public static final String SOIL = "soil";
    public static final String ORIGIN = "origin";
    public Flower(){}
    private String name;
    private String soil;
    private String origin;
    private VisualParameters parameters;
    private GrowingTips tips;

    public void setName(String name)
    {
        this.name = name;
    }

    public void setSoil(String soil)
    {
        this.soil = soil;
    }

    public void setOrigin(String origin)
    {
        this.origin = origin;
    }

    public void setParameters(VisualParameters parameters)
    {
        this.parameters = parameters;
    }

    public void setTips(GrowingTips tips)
    {
        this.tips = tips;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public String toString()
    {
        return "Flower{" +
                "name='" + name + '\'' +
                ", soil='" + soil + '\'' +
                ", origin='" + origin + '\'' +
                ", parameters=" + parameters +
                ", tips=" + tips +
                '}';
    }
}
