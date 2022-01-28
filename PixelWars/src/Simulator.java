public class Simulator {
    public static void main(String[] args) throws InterruptedException {
        Board board = new Board(1000 / 4, 500 / 4);
        board.generate();

        int playerCount = 100; // Change this
        Clan[] players = new Clan[playerCount];

        for (int i = 0; i < playerCount; i++) {
            players[i] = new Clan(board);
            board.draw(players[i].getCoreX(), players[i].getCoreY(), players[i].getColor());
        }

        board.fill(players);

        StdDraw.enableDoubleBuffering();

        Board currentBoard = new Board(board.getWidth(), board.getHeight());
        int seconds = 0;

        while (seconds < 1000000) {
            seconds++;

            for (int x = 0; x < board.getWidth(); x++) {
                for (int y = 0; y < board.getHeight(); y++) {
                    currentBoard.setCell(x, y, board.getCell(x, y));
                }
            }

            for (int x = 0; x < board.getWidth(); x++) {
                for (int y = 0; y < board.getHeight(); y++) {
                    Clan player = currentBoard.getCell(x, y);

                    if (player != null) {
                        currentBoard.draw(x, y, player.getColor());
                        player.spread(x, y);
                    }
                }
            }

            StdDraw.show();
            Thread.sleep(50);
        }
    }
}
