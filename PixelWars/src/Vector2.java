class Vector2 {
    private int x;
    private int y;

    public Vector2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getDistanceFromCore(Vector2 core) {
        int x = Math.abs(this.x - core.x);
        int y = Math.abs(this.y - core.y);

        int result = (int) Math.round(Math.sqrt((x * x) + (y * y)));
        return result;
    }
}
