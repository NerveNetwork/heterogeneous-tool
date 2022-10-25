package network.nerve;

import org.junit.Test;

import javax.script.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class JscriptTest {

    @Test
    public void test() {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        if (!(engine instanceof Invocable)) {
            System.out.println("Invoking methods is not supported.");
            return;
        }

        Invocable inv = (Invocable) engine;
        File file = new File(JscriptTest.class.getClassLoader().getResource("js/test.js").getFile());

        try {
            engine.eval(new FileReader(file));
            // 获取对象
            Object calculator = engine.get("calculator");

            int x = 3;
            int y = 4;
            Object addResult = inv.invokeMethod(calculator, "add", x, y);
            Object subResult = inv.invokeMethod(calculator, "subtract", x, y);
            Object mulResult = inv.invokeMethod(calculator, "multiply", x, y);
            Object divResult = inv.invokeMethod(calculator, "divide", x, y);

            System.out.println(addResult);
            System.out.println(subResult);
            System.out.println(mulResult);
            System.out.println(divResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
