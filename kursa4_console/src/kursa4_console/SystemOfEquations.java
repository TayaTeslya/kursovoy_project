package kursa4_console;

/*
 * Модуль SystemOfEquations
 * Переменные, используемые в модуле:
 * x - двумерный массив для коэффициентов при неизвестных
 * b - массив для свободных членов
 * Подпрограммы модуля:
 * getX - функция для получения коэффициентов при неизвестных
 * getB - функция для получения свободных членов
 * */


public class SystemOfEquations {
	
	private double[][] x;
	private double[] b;
	
	SystemOfEquations(double[][] x, double[] b) {
		this.x = x;
		this.b = b;
	}
	
	/*
	 * Функция для получения коэффициентов при неизвестных
	 * Формальные параметры:
	 * x - коэффициенты при неизвестных
	*/
	public double[][] getX() {
		return this.x;
	}
	
	/*
	 * Функция для получения свободных членов
	 * Формальные параметры:
	 * b - свободные члены
	*/
	public double[] getB() {
		return this.b;
	}
	
}
