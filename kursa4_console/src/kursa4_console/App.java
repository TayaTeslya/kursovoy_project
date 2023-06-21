package kursa4_console;
import java.util.Arrays;
import java.util.Scanner;

/*
 *  Курсовой проект
 *  по предмету МДК.01.01 Разработка программных модулей
 *  по теме "Решение систем линейных уравнений"
 *  Язык: Java
 *  Разработал: Тесля Т.Е.
 *  
 *  Задание:
 *  Разработка программ интерполяции функций с использованием:
 *  1. Метод Гаусса
 *  2. Метод квадратных корней
 *  
 *  Основные переменные, используемые в программе:
 *  len - количество уравнений и неизвестных
 *  
 *  Вызываемые подпрограммы:
 *  menu - вызов меню
 *  method - решение системы выбранным методом
 *  inputEquations - ввод коэффициентов при неизвестных и свободных членов системы
 *  methodGausse - решение методом Гаусса
 *  trilangularMatrixByGausseMethod - приведение к верхнетреугольной матрице
 *  reverserGausse - обратный проход метода Гаусса
 *  methodSquareRoots - решение методом квадратных корней
 *  recursiveDet - рекурсивная функция для поиска минора матрицы
 *  findDet - подсчёт определителя матрицы
*/

public class App {
	
	public static int len;
	
	/*
	 * Функция вызова меню
	 * Формальные параметры:
	 * m - пункт меню 
	*/
	public static void menu() {
		
		Scanner scanner = new Scanner(System.in); // Объект сканер для ввода данных
		String m = "0"; // Переменная для выбора пункта меню
		
		while (m != "3") { // Пока не выбран пункт меню "Выйти"
			System.out.println("------------------------------------");
			System.out.println("Меню:");
			System.out.println("------------------------------------");
			System.out.println("1 - Решить СЛУ методом Гаусса");
			System.out.println("2 - Решить СЛУ методом квадратных корней");
			System.out.println("3 - Выйти");
			System.out.println("------------------------------------");
			m = scanner.nextLine(); // Ввод пункта меню
			switch (m) { // Вызоа функций по выбранному пункту меню
				case "1": // Метод Гаусса
					method("methodGauss"); 
					break;
				case "2":  // Метод квадратных корней
					method("methodSquareRoots");
					break;
				case "3": // Выход из приложения
					System.out.println("До свидания!");
					m = "3"; // Для обновления переменной из-за различия типов сканера и условия цикла
					System.exit(0); // Закрытие приложение с ошибкой 0 (нет ошибок)
					break;
				default: // При вводе символа, не указанного в пунктах меню
					System.out.println("Пожалуйста, введите нужный вам пункт");
			}
			System.out.println("\n");
		}
		
	}
	
	/*
	 * Функция решения системы линейных уравнений
	 * Формальные параметры:
	 * method - выбранный метод решения
	 * 
	 * systemOfEquations - объект с коэффициентами при неизвестных и свободных членов системы
	 * m - переменная для выхода в меню
	*/
	public static void method(String method) {
		
		SystemOfEquations systemOfEquations = inputEquations(); // ввод коэффициентов системы уравнений
		
		System.out.println("Полученная система линейных уравнений: "); // вывод системы уравнений
		for (int i = 0; i < len; i++) {
			for (int j = 0; j < len; j++) {
				System.out.print(systemOfEquations.getX()[i][j] + "x" + (j + 1));
				if (j + 1 == len) {
					System.out.print(" = ");
					break;
				}
				System.out.print(" + ");
			}
			System.out.println(systemOfEquations.getB()[i]);
		}
		System.out.println();
		
		// Решение СЛУ выбранным методом
		if (method == "methodGauss") { // Метод Гаусса
			methodGauss(systemOfEquations);
		}
		else { // Метод квадратных корней
			methodSquareRoots(systemOfEquations);
		}
		
		Scanner scanner = new Scanner(System.in); // Объект сканер для ввода данных
		String m = ""; // Переменная для возвращения в меню
		while (m == "") {
			System.out.print("\n" + "Введите любой символ для завершения: ");
			m = scanner.nextLine();
		}
		System.out.println("\n\n\n\n\n");
		
	}
	
	
	/*
	 * Функция ввода коэффициетов при неизвестных и свободных членов системы
	 * Формальные параметры:
	 * scanner - объект для ввода данных
	 * n - количество уравнений и неизвестных
	 * x - двумерный массив для коэффициентов при неизвестных
	 * b - одновмерный массив для свободных членов
	 * go - булевая переменная для проверки корректности введенных данных
	 * str - переменная для ввода коэффициентов при неизвестных и свободных членов
	*/
	public static SystemOfEquations inputEquations() {
		
		Scanner scanner = new Scanner(System.in); // Объект сканер для ввода данных
		int n = 0; // кол-во уравнений и неизвестных
		
		while (n < 2) { // Пока не будет введено кол-во неизвестных больше 1
			System.out.print("Введите кол-во уравнений и неизвестных (больше 1): ");
			try {
				n = Integer.valueOf(scanner.nextLine());
			} catch (Exception e) { }
		}
		len = n;
		System.out.println();
		
		double[][] x = new double[n][n]; // Матрица неизвестных
		double[] b = new double[n]; // Матрица свободных членов
		
		// Ввод коэффициентов
		boolean go;
		String str;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				go = false;
				while (!go) { // Проверка корректности введенных данных
					str = "";
					System.out.print("Введите коэффициент x" + (j + 1) + " " + (i + 1) + " уравнения: ");
					while (str == "") {
						str = scanner.nextLine();
					}
					if (str != "") {
						try {
							x[i][j] = Double.valueOf(str.replace(',', '.'));
							go = true;
						} 
						catch (Exception e) { }	
					}
				}				
			}
			go = false;
			while (!go) { // Проверка корректности введенных данных
				str = "";
				System.out.print("Введите свободный член уравнения (после =): ");
				while (str == "") {
					str = scanner.nextLine();
				}
				if (str != "") {
					try {
						b[i] = Double.valueOf(str.replace(',', '.'));
						go = true;
					} 
					catch (Exception e) { }	
				}
			}	
		}
		System.out.println();
		
		return new SystemOfEquations(x, b); // Возвращаем объект "СЛУ" с матрицей неизвестных и матрицей свободных членов
				
	}

	/*
	 * Функция решения системы методом Гаусса
	 * Формальные параметры:
	 * systemOfEquations - объект с коэффициентами при неизвестных и свободных членов
	 * 
	 * trilangularMatrix - объект, хранящий верхнетреугольную матрицу
	 * x - массив для нахождения неизвестных
	 * str - массив для поочередного сохранения строк системы
	 * b - переменная свободного члена строки
	*/
	public static void methodGauss(SystemOfEquations systemOfEquations) {
		
			System.out.println("Метод Гаусса:\n");
			
			// Прямой проход
			SystemOfEquations trilangularMatrix = triangularMatrixByGaussMethod(systemOfEquations); // Приведение к верхнетреугольной матрице
			
			// Проверка треугольной матрицы на пустоту (если пустая, решить нельзя)
			if (trilangularMatrix.getX() == null) {
				return;
			}
 			
			System.out.println("Верхнетреугольная матрица: ");
			for (int i = 0; i < len; i++) { // Вывод верхнетреугольной матрицы
				for (int j = 0; j < len; j++) {
					System.out.print(trilangularMatrix.getX()[i][j] + " ");
				}
				System.out.println(" |  " + trilangularMatrix.getB()[i]);
			}
			System.out.println();
			
			// Обратный проход
			System.out.println("2. С помощью обратного прохода узнаем значения неизвестных:\n");
			double[] x = new double[len]; // Массив для нахождения неизвестных
			Arrays.fill(x, 0); // Заполнение массива нулями
			double[] str = new double[len]; // Массив для сохранения строк СЛУ поочередно (начиная с конца)
			double b; // Переменная свободного члена строки
			for (int i = len - 1; i >= 0; i--) {
				b = trilangularMatrix.getB()[i];
				for (int j = 0; j < len; j++) {
					str[j] = trilangularMatrix.getX()[i][j]; // Сохранение каждого элемента строки в массив для нахождения неизвестных
				}
				x[i] = reverseGauss(str, b, x, i); // Вызов функции нахождения неизвестных
			}
			
			System.out.println("\nНайденные неизвестные: "); // Вывод найденных неизвестных
			for (int i = 0; i < len; i++) {
				System.out.println("x" + (i + 1) + " = " + x[i]);
			}
			System.out.println();
			
	}
	
	/*
	 * Функция приведения к верхнетреугольной матрице - прямой проход метода Гаусса
	 * Формальные параметры:
	 * systemOfEquations - объект с коэффициентами при неизвестных и свободных членов
	 * 
	 * x - двумерный массив для коэффициетов при неизвестных
	 * b - массив свободных членов
	 * max - массив для сохранения строки и столбца, на котором находится максимальный элемент
	 * k - коэффициент для деления строк матрицы, чтобы получать 1 на главной диагонали
	*/
	public static SystemOfEquations triangularMatrixByGaussMethod(SystemOfEquations systemOfEquations) {
		
		System.out.println("1. Приведём матрицу к верхнетреугольной таким образом, чтобы элементы главной диагонали были равны 1:\n");
		
		double[][] x = systemOfEquations.getX().clone(); // Матрица коэффициентов при неизвестных
		double[] b = systemOfEquations.getB().clone(); // Матрица свободных членов
		
		int[] max = new int[2]; // 0 - i; 1 - j - строка и столбец, на котором находится максимальный элемент столбца
		
		System.out.println("1.1. Переставим строки так, чтобы на главной диагонали находились максимальные элементы\n");
		
		for (int j = 0; j < len; j++) {
			max[0] = j; // Ставим изначальный максимальный элемент - диагональный
			max[1] = j;
			for (int i = j; i < len; i++) {
				if (Math.abs(x[i][j]) > Math.abs(x[max[0]][max[1]])) {
					max[0] = i; // Если нашли элемент больше диагонального, сохраняем строчку, на которой находится максимальный элемент
				}
			}
			
			System.out.println("\n" + "Максимальный элемент " + (j + 1) + " столбца = " + x[max[0]][max[1]]);
			if (max[0] != j) { // если i и j не совпадают, то максимальный элемент стоблца находится не на диагонали
				System.out.println("=> Меняем местами " + (j + 1) + " и " + max[0] + " строки:");
				double dop = b[max[0]]; // max[0] - строка с максимумом
				b[max[0]] = b[j]; // j - строка с диагональю 
				b[j] = dop;
				for (int i = 0; i < len; i++) { // i - номер столбца
					dop = x[j][i];
					x[j][i] = x[max[0]][i];
					x[max[0]][i] = dop;
				}
			}
			else if (x[max[0]][max[1]] != 0) {
				System.out.println("Максимальный элемент уже находится на главной диагонали.");
			}
			else {
				System.out.println("=> x" + (j + 1) + " неизвестный невозможно найти из данной системы линейных уравнений");
				return new SystemOfEquations(null, null);
			}
			System.out.println();
			for (int i = 0; i < len; i++) {
				for (int ii = 0; ii < len; ii++) {
					System.out.print(x[i][ii] + " ");
				}
				System.out.println("| " + b[i]);
			}
			System.out.println("");
			
		}
		
		System.out.println("1.2. Приведем получившуюся матрицу к верхнетреугольной\n");
		
		double k;
		for (int i = 0; i < len; i++) {
			System.out.println("Работаем с " + (i + 1) + " строкой.");
			for (int j = 0; j <= i; j++) {
				k = x[i][j];
				if (k != 0) {
					if (k != 1) {
						System.out.println("Делим каждый элемент на x" + (j + 1) + " = " + x[i][j] + ", чтобы получить 1 у элемента x" + (j + 1));
					}
					if (i != j) {
						System.out.println("Из " + (j + 1) + " строки поэлементно вычитаем текущие элементы " + (i + 1) + " строки");
					}
					for (int l = 0; l < len ; l++) {
						if (x[i][l] != 0 && k != 1) {
							System.out.println("x" + (l + 1) + " / " + k + " = " + x[i][l] + " / " + k + " = " + x[i][l] / k);
							x[i][l] /= k;
						}
						if (i != j)  {
							System.out.println("x" + (i + 1) + (l + 1) + " - x" + (j + 1) + (l + 1)  + " = " + x[i][l] + " - " + x[j][l] + " = " + (x[j][l] - x[i][l]));
							x[i][l] = x[j][l] - x[i][l];
						}
					}
					if (b[i] != 0 && k != 1)  {
						System.out.println("b" + (i + 1) + " / " + k + " = " + b[i] + " / " + k + " = " + (b[i] / k));
						b[i] /= k;
					}
					if (i != j) {
						System.out.println("b" + (i + 1) + " - b" + (j + 1) + " = " + b[i] + " - " + b[j] + " = " + (b[j] - b[i]));
						b[i] = b[j] - b[i];
					}
					System.out.println("\nПолучаем матрицу:");
					for (int ii = 0; ii < len; ii++) {
						for (int jj = 0; jj < len; jj++) {
							System.out.print(x[ii][jj] + " ");
						}
						System.out.println("= " + b[ii]);
					}
					System.out.println();
				}
			}	
		}
		
		return new SystemOfEquations(x, b);
		
	}
		
	/*
	 * Функция обратного прохода
	 * Формальные параметры:
	 * str - коэффициенты при неизвестных строки
	 * b - свободный член строки
	 * k - найденные неизвестные
	 * i - индекс искомого неизвестного
	 * 
	 * x - искомый неизвестный
	*/
	public static double reverseGauss(double[] str, double b, double[] k, int i) {
		
		double x = b;
		for (int j = 0; j < len; j++) {
			if (j != i && str[j] != 0) {
				x -= str[j] * k[j];
			}
		}
		if (str[i] != 0 && str[i] != 1 && x != 0) {
			x /= str[i];
		}
		return x;
		
	}
	
	/*
	 * Функция решения методом квадратных корней
	 * Формальные параметры:
	 * systemOfEquations - объект с коэффициентами при неизвестных и свободных членов
	 * 
	 * x - двумерный массив для коэффициетов при неизвестных
	 * b - массив свободных членов
	 * det - определитель матрицы
	 * g - треугольная G-матрица
	 * y - свободные члены G-матрицы
	 * sum - подсчет суммы для формул
	 * str - коэффициенты при неизвестных строки
	 * xx - неизвестные
	*/
	public static void methodSquareRoots(SystemOfEquations systemOfEquations) {
		
		double[][] x = systemOfEquations.getX();
		double[] b = systemOfEquations.getB();
		System.out.println("Метод квадратных корней: " + "\n");

		// Проверка на симметричность матриц
		for (int i = 0; i < len; i++) {
			if (x[i][len - i - 1] != x[len - i - 1][i]) {
				System.out.println("Матрица нессиметричная,  метод квадратных корней не подходит.");
				return;
			}
		}
		
		double det = recursiveDet(systemOfEquations.getX()); //Нахождение определителя матрицы 
		if (det != 0) {
			// прямой ход
			double[][] g = new double[len][len];
			double sum;
			for (int i = 0; i < len; i++) {
				Arrays.fill(g[i], 0);
			}
			if (x[0][0] < 0) {
				System.out.println("Данная СЛУ не подходит для решения этим методом.");
				return;
			}
			g[0][0] = Math.sqrt(x[0][0]); // 1 элемент матрицы g
			for (int j = 0; j < len; j++) {
				if (x[0][j] != 0 && g[0][0] != 0) {
					g[0][j] = x[0][j] / g[0][0]; // 1 строка матрицы g
				}
			}
			for (int i = 1; i < len; i++) {
				// поиск диагонального элемента
				sum = 0;
				for (int k = 0; k < i; k++) {
					sum += g[k][i] * g[k][i];
				}
				if ((x[i][i] - sum) < 0) {
					System.out.println("Данная СЛУ не подходит для решения этим методом.");
					return;
				}
				if ((x[i][i] - sum) != 0) {
					g[i][i] = Math.sqrt(x[i][i] - sum);
				}
				// поиск остальных элементов строки
				for (int j = (i + 1); j < len; j++) {
					sum = 0;
					for (int k = 0; k < i; k++) {
						sum += g[k][i] * g[k][j];
					}
					if ((x[i][j] - sum) != 0 && g[i][i] != 0) {
						g[i][j] = (x[i][j] - sum) / g[i][i];
					}
				}
			}
			
			// обратный ход
			// находим y
			double[] y = new double[len];
			y[0] = b[0] / g[0][0];
			for (int i = 1; i < len; i++) {
				sum = 0;
				for (int k = 0; k < i; k++) {
					sum += g[k][i] * y[k];
				}
				if (g[i][i] != 0 && (b[i] - sum) != 0) {
					y[i] = (b[i] - sum) / g[i][i];
				}
			}
			
			System.out.println("G - матрица | y");
			for (int i = 0; i < len; i++) { // вывод g матрицы
				for (int j = 0; j < len; j++) {
					System.out.print(g[i][j] + " ");
				}
				System.out.println("| " + y[i]);
			}
			
			// находим x
			double[] str = new double[len];
			double[] xx = new double[len];
			Arrays.fill(xx, 0);
			for (int i = (len - 1); i >= 0; i--) {
				for (int j = 0; j < len; j++) {
					str[j] = g[i][j]; // Сохранение каждого элемента строки в массив для нахождения неизвестных
				}
				xx[i] = reverseGauss(str, y[i], xx, i); // Вызов функции нахождения неизвестных
			}
						
			System.out.println("\nНайденные х:");
			for (int i = 0; i < len; i++) {
				System.out.print("x" + (i + 1) + " = " + xx[i] + " ");
			}
			System.out.println();
			
		}
		else {
			System.out.println("Определитель матрицы данной СЛУ равен 0");
			System.out.println("СЛУ имеет несколько решений");
			System.out.println("Метод квадратных корней не подходит");
			return;
		}
		
	}
	
	/*
	 * Рекурсивная функция нахождения минора матрицы
	 * Формальные параметры:
	 * x - двумерный массив с коэффициентами при неизвестных
	 * 
	 * det - определитель матрицы
	 * dopDet - дополнительная переменная для нахождения определителя матрицы
	 * ii, jj - индексы строк и столбцов для создания минора матрицы
	*/
	public static double recursiveDet(double[][] x) {
		
		double det = 0;
		double dopDet;
		
		if (x.length == 2) {
			dopDet = findDet(x[0][0], x[1][1], x[0][1], x[1][0]);
			return dopDet; // выход из процедуры
		}
		else {
			for (int k = 0; k < x.length; k++) {
				if (k % 2 == 0 || k == 0) {
					dopDet = x[k][0]; // то, что умножается на определитель минора матрицы
				}
				else {
					dopDet = -x[k][0];
				}
				
				double[][] dopX = new double[x.length - 1][x.length - 1]; // минорная матрицы
				int ii = 0;
				int jj = 0;
				for (int i = 0; i < x.length; i++) { // сохранение минора матрицы
					jj = 0;
					if (i != k) { // если не строка с коэффициентом
						for (int j = 1; j < x.length; j++) {
							dopX[ii][jj] = x[i][j];
							jj++;
						}
						ii++;
					}
				}
				dopDet *= recursiveDet(dopX);
				det += dopDet;
			}	
		}
		
		return det;
		
	}
	
	/*
	 * Функция нахождения определителя матрицы
	 * Формальные параметры:
	 * a - 1 элемент 1 строки минора матрицы
	 * c - 2 элемент 2 строки минора матрицы
	 * b - 1 элемент 2 строки минора матрицы
	 * d - 2 элемент 1 строки минора матрицы
	 * 
	 * det - определитель минора матрицы
	*/
	public static double findDet(double a, double c, double b, double d) {
		
		double det = a * c - d * b;
		return det;
		
	}
	
	/*
	 * Главная функция программы
	*/
	public static void main(String[] args) {
			
		menu();
			
	}

}
