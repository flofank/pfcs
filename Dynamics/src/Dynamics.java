public abstract class Dynamics {
	
	double[] euler(double[] x, double dt) {
		double[] y = f(x);
		double[] res = new double[x.length];
		for (int i = 0; i < x.length; i++) {
			res[i] = x[i] + y[i] * dt;
		}
		return res;
	}
	
	abstract double[] f(double[] x);
	
	double[] runge(double[] x, double dt) {
		int n = x.length;
		double[] y1 = f(x);
		double[] xx = new double[n];
		for(int i = 0; i < n; i++) {
			xx[i] = x[i] + y1[i] * dt / 2;
		}
		double[] y2 = f(xx);
		for(int i = 0; i < n; i++) {
			xx[i] = x[i] + y2[i] * dt / 2;
		}
		double[] y3 = f(xx);
		for(int i = 0; i < n; i++) {
			xx[i] = x[i] + y3[i] * dt;
		}
		double[] y4 = f(xx);
		double[] y = new double[n];
		for (int i = 0; i < n; i++) {
			y[i] = 1.0/6 * (y1[i] + y2[i] * 2 + y3[i] * 2 + y4[i]);
		}
		double[] res = new double[n];
		for (int i = 0; i < n; i++) {
			res[i] = x[i] + y[i] * dt;
		}
		return res;
	}
}
