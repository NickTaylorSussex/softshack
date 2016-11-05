import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ReadWriteCsv {

	public static void main(String[] args) throws IOException {
		// declaring variables and initialising them
		FileInputStream inputStream = null;
		FileInputStream inputStream2 = null;
		Scanner sc = null;
		Scanner sc2 = null;
		String csvSeparator = ",";
		String line;
		String line2;
		String path = "C:/PriceDataDB/newCSVJustMyData.csv";
		String path2 = "C:/PriceDataDB/ukpostcodes.csv";
		String path3 = "D:/PriceDataDB/newCSVPriceLocations.csv";
		String[] columns;
		String[] columns2;
		Boolean matchFound = false;
		int count = 0;

		StringBuilder builder = new StringBuilder();
		FileWriter writer = new FileWriter(path3);

		try {
			// specifies where to take the files from
			inputStream = new FileInputStream(path);
			inputStream2 = new FileInputStream(path2);

			// creating scanners for file1
			sc = new Scanner(inputStream, "UTF-8");

			// while there is another line available do:
			while (sc.hasNextLine()) {
				count++;
				// storing the current line in the temporary variable "line"
				line = sc.nextLine();
				System.out.println("Number of lines read so far: " + count);
				// defines the columns[] as the line being split by ","
				columns = line.split(",");
				inputStream2 = new FileInputStream(path2);
				sc2 = new Scanner(inputStream2, "UTF-8");

				// check if there is a line available in File2, read file2 and
				// compare the desired elements in the array
				while (!matchFound && sc2.hasNextLine()) {
					line2 = sc2.nextLine();
					columns2 = line2.split(",");
					if (columns[0].equals(columns2[1])) {
						matchFound = true;
						// build up the necessary data in the builder 
						// and write the result
						builder.append(columns[0]).append(csvSeparator);
						builder.append(columns[1]).append(csvSeparator);
						builder.append(columns2[2]).append(csvSeparator);
						builder.append(columns2[3]).append("\n");
						String result = builder.toString();
						writer.write(result);
					}

				}
				// reset builder and close scanner for file2
				builder.setLength(0);
				sc2.close();
				matchFound = false;
			}

			if (sc.ioException() != null) {
				throw sc.ioException();

			}

		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
			if (inputStream2 != null) {
				inputStream2.close();
			}

			if (sc != null) {
				sc.close();
			}
			if (sc2 != null) {
				sc2.close();

			}
			writer.close();
			System.out.println("Writing Complete!");
		}

	}

}
