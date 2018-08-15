public class ConcreteFactory extends AbStractFactory{
    @Override
    public FoodStuff produceRice() {
        return new Rice();
    }

    @Override
    public FoodStuff produceMillet() {
        return new Millet();
    }

    @Override
    public FoodStuff produceCorn() {
        return new Corn();
    }
}
