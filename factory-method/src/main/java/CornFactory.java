public class CornFactory implements IFactory{
    @Override
    public FoodStuff produceFoodStuff() {
        return new Corn();
    }
}
