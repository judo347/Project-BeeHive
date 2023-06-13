package dk.mk.bee.game.map;

import dk.mk.bee.game.config.GameInfo;
import dk.mk.bee.game.exception.IllegalMapSize;
import dk.mk.bee.ui.config.VisualInfo;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class GameMapTests {
    @Test
    void when_game_map_is_larger_then_screen_throw_IllegalMapSize_exception() {
        try (MockedStatic<GameInfo> gameInfo = Mockito.mockStatic(GameInfo.class)) {
            try (MockedStatic<VisualInfo> visualInfo = Mockito.mockStatic(VisualInfo.class)) {
                gameInfo.when(GameInfo::GAME_SQUARE_COUNT_HORIZONTAL).thenReturn(2);
                gameInfo.when(GameInfo::GAME_SQUARE_COUNT_VERTICAL).thenReturn(2);
                visualInfo.when(VisualInfo::SQUARE_SIZE).thenReturn(10);
                visualInfo.when(VisualInfo::WINDOW_WIDTH).thenReturn(10);
                visualInfo.when(VisualInfo::WINDOW_HEIGHT).thenReturn(10);

                assertThrows(IllegalMapSize.class, GameMap::new);
            }
        }
    }

    @Test
    void when_game_map_is_initialized_successfully_then_map_size_should_match_specification() {
        try (MockedStatic<GameInfo> gameInfo = Mockito.mockStatic(GameInfo.class)) {
            try (MockedStatic<VisualInfo> visualInfo = Mockito.mockStatic(VisualInfo.class)) {
                int mapSizeHorizontal = 5;
                int mapSizeVertical = 7;

                gameInfo.when(GameInfo::GAME_SQUARE_COUNT_HORIZONTAL).thenReturn(mapSizeHorizontal);
                gameInfo.when(GameInfo::GAME_SQUARE_COUNT_VERTICAL).thenReturn(mapSizeVertical);
                visualInfo.when(VisualInfo::SQUARE_SIZE).thenReturn(10);
                visualInfo.when(VisualInfo::WINDOW_WIDTH).thenReturn(2400);
                visualInfo.when(VisualInfo::WINDOW_HEIGHT).thenReturn(1000);

                GameMap map = new GameMap();

                assertEquals(mapSizeHorizontal, map.width());
                assertEquals(mapSizeVertical, map.height());
                assertEquals(mapSizeHorizontal, map.map()[0].length);
                assertEquals(mapSizeVertical, map.map().length);
            }
        }
    }

    @Test
    void when_game_map_is_initialized_successfully_border_should_be_solid() {
        try (MockedStatic<GameInfo> gameInfo = Mockito.mockStatic(GameInfo.class)) {
            try (MockedStatic<VisualInfo> visualInfo = Mockito.mockStatic(VisualInfo.class)) {
                int mapSizeHorizontal = 5;
                int mapSizeVertical = 7;

                gameInfo.when(GameInfo::GAME_SQUARE_COUNT_HORIZONTAL).thenReturn(mapSizeHorizontal);
                gameInfo.when(GameInfo::GAME_SQUARE_COUNT_VERTICAL).thenReturn(mapSizeVertical);
                visualInfo.when(VisualInfo::SQUARE_SIZE).thenReturn(10);
                visualInfo.when(VisualInfo::WINDOW_WIDTH).thenReturn(2400);
                visualInfo.when(VisualInfo::WINDOW_HEIGHT).thenReturn(1000);

                GameMap map = new GameMap();

                assertTrue(((GameStructure) map.map()[0][0]).isSolid());
                assertTrue(((GameStructure) map.map()[0][1]).isSolid());
                assertTrue(((GameStructure) map.map()[0][2]).isSolid());
                assertTrue(((GameStructure) map.map()[0][3]).isSolid());
                assertTrue(((GameStructure) map.map()[0][4]).isSolid());

                assertTrue(((GameStructure) map.map()[6][0]).isSolid());
                assertTrue(((GameStructure) map.map()[6][1]).isSolid());
                assertTrue(((GameStructure) map.map()[6][2]).isSolid());
                assertTrue(((GameStructure) map.map()[6][3]).isSolid());
                assertTrue(((GameStructure) map.map()[6][4]).isSolid());

                assertTrue(((GameStructure) map.map()[1][0]).isSolid());
                assertTrue(((GameStructure) map.map()[2][0]).isSolid());
                assertTrue(((GameStructure) map.map()[3][0]).isSolid());
                assertTrue(((GameStructure) map.map()[4][0]).isSolid());
                assertTrue(((GameStructure) map.map()[5][0]).isSolid());

                assertTrue(((GameStructure) map.map()[1][4]).isSolid());
                assertTrue(((GameStructure) map.map()[2][4]).isSolid());
                assertTrue(((GameStructure) map.map()[3][4]).isSolid());
                assertTrue(((GameStructure) map.map()[4][4]).isSolid());
                assertTrue(((GameStructure) map.map()[5][4]).isSolid());

                assertFalse(((GameStructure) map.map()[1][1]).isSolid());
                assertFalse(((GameStructure) map.map()[1][3]).isSolid());
                assertFalse(((GameStructure) map.map()[5][1]).isSolid());
                assertFalse(((GameStructure) map.map()[5][3]).isSolid());
            }
        }
    }
}
