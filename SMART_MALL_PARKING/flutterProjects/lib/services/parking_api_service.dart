import 'dart:convert';
import 'package:http/http.dart' as http;
import '../models/parking_spot_from_api.dart'; // modelul pentru locurile din API

class ParkingApiService {
  static const String baseUrl = 'http://10.0.2.2:8080/api/locuri';

  static Future<List<ParkingSpotFromApi>> getParkingSpots() async {
    try {
      final response = await http.get(Uri.parse(baseUrl));
      if (response.statusCode == 200) {
        final data = jsonDecode(response.body) as List;
        return data.map((e) => ParkingSpotFromApi.fromJson(e)).toList();
      }
      return [];
    } catch (e) {
      print('Eroare la preluarea locurilor: $e');
      return [];
    }
  }
}
