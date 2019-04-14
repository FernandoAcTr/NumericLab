package nolinearsystems.model;

import utils.MyUtils;

public class NoLinearSolver {
    private Function F1;
    private Function F2;
    private Function F1x, F1y;
    private Function F2x, F2y;
    private double errorPermited;
    private String procedure;

    public NoLinearSolver(String f1, String f2, String f1x, String f1y, String f2x, String f2y, double errorPermited) {
        F1 = new Function(f1);
        F2 = new Function(f2);
        F1x = new Function(f1x);
        F1y = new Function(f1y);
        F2x = new Function(f2x);
        F2y = new Function(f2y);
        this.errorPermited = errorPermited;
        procedure = String.format("%-6s\t%-16s\t%-16s\t%-16s\t%-16s\t%-16s\t%-16s\t%-16s\t%-16s\t\t%-16s\t%-16s\t%-16s\t%-16s\t%-16s\t\t%-16s",
                "No.", "x", "y", "f1", "f2", "f1x", "f1y", "f2x", "f2y", "Δx", "Δy", "Xi+1", "Yi+1", "ep1", "ep2");
    }

    /**
     * Resuelve el sistema de ecuaciones por el metodo de Newton-Raphson Multivariable
     * @param x Valor inicial en x
     * @param y Valor inicial en Y
     * @return Arreglo con resultados para x y para y
     * @throws Exception
     */
    public double[] resolvByNewton_Raphson_Multivariable(double x, double y) throws Exception {
        int iteration = 0;
        double valF1, valF2, valF1x, valF1y, valF2x, valF2y, valDeltaX, valDeltaY, valNextX, valNextY, valEp1, valEp2;
        do {

            valF1 = F1.evaluateFrom(x, y);
            valF2 = F2.evaluateFrom(x, y);
            valF1x = F1x.evaluateFrom(x, y);
            valF1y = F1y.evaluateFrom(x, y);
            valF2x = F2x.evaluateFrom(x, y);
            valF2y = F2y.evaluateFrom(x, y);
            valDeltaX = (-valF1 * valF2y + valF2 * valF1y) / (valF1x * valF2y - valF2x * valF1y);
            valDeltaY = (-valF2 * valF1x + valF1 * valF2x) / (valF1x * valF2y - valF2x * valF1y);
            valNextX = x + valDeltaX;
            valNextY = y + valDeltaY;
            valEp1 = (valNextX - x) / valNextX * 100;
            valEp2 = (valNextY - y) / valNextY * 100;

            iteration++;

            concatProcedure(iteration, x, y, valF1, valF2, valF1x, valF1y, valF2x, valF2y, valDeltaX, valDeltaY, valNextX,
                    valNextY, valEp1, valEp2);

            x = valNextX;
            y = valNextY;

        } while (valEp1 > errorPermited && valEp2 > errorPermited);

        return new double[]{valNextX, valNextY};
    }

    private void concatProcedure(int iteration, double x, double y, double f1, double f2, double f1x, double f1y, double f2x, double f2y,
                                 double deltaX, double deltaY, double nextX, double nextY, double ep1, double ep2) {
        String sX = MyUtils.format(x);
        String sY = MyUtils.format(y);
        String sF1 = MyUtils.format(f1);
        String sF2 = MyUtils.format(f2);
        String sF1x = MyUtils.format(f1x);
        String sF1y = MyUtils.format(f1y);
        String sF2x = MyUtils.format(f2x);
        String sF2y = MyUtils.format(f2y);
        String sDeltaX = MyUtils.format(deltaX);
        String sDeltaY = MyUtils.format(deltaY);
        String sNextX = MyUtils.format(nextX);
        String sNextY = MyUtils.format(nextY);
        String sEp1 = MyUtils.format(ep1);
        String sEp2 = MyUtils.format(ep2);

        String line;
        if(iteration < 2)
        line = String.format("%-6s\t%-16s\t%-16s\t%-16s\t%-16s\t%-16s\t%-16s\t%-16s\t%-16s\t%-16s\t%-16s\t%-16s\t%-16s\t%-16s\t%-16s",
                iteration, sX, sY, sF1, sF2, sF1x, sF1y, sF2x, sF2y, sDeltaX, sDeltaY, sNextX, sNextY, sEp1, sEp2);
        else
            line = String.format("%-6s\t%-16s\t%-16s\t%-16s\t%-16s\t%-16s\t\t%-16s\t%-16s\t%-16s\t\t%-16s\t%-16s\t%-16s\t%-16s\t%-16s\t%-8s",
                    iteration, sX, sY, sF1, sF2, sF1x, sF1y, sF2x, sF2y, sDeltaX, sDeltaY, sNextX, sNextY, sEp1, sEp2);

        procedure += "\n"+line;
    }

    public String getProcedure() {
        return procedure;
    }
}
