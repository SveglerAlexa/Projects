import 'dart:convert';
import 'package:http/http.dart' as http;
import '../models/rezervare_from_api.dart';
import '../models/sesiune_parcare_dto.dart';
import '../models/sofer_model.dart';
import '../models/parking_spot_from_api.dart';

class RezervareApiService {
  static const String baseUrl = 'http://10.0.2.2:8080'; // emulator Android

  // trimite cererea de rezervare pentru sofer
  static Future<ParkingSpotFromApi?> rezervaLoc(Sofer sofer) async {
    final response = await http.post(
      Uri.parse('$baseUrl/api/rezervari/rezervare'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode({'soferId': sofer.id}),
    );

    if (response.statusCode == 200) {
      final data = jsonDecode(response.body);
      return ParkingSpotFromApi.fromJson(data);
    } else {
      throw Exception('Eroare la rezervare: ${response.statusCode}');
    }
  }


  static Future<ParkingSpotFromApi?> getParcareActiva(int soferId) async {
    final url = Uri.parse('$baseUrl/api/rezervari/parcare-activa/$soferId');
    final response = await http.get(url);

    if (response.statusCode == 200) {
      // asigură-te că body nu e gol
      if (response.body.isEmpty) return null;

      final jsonData = jsonDecode(response.body);
      if (jsonData == null) return null; // extra protecție
      return ParkingSpotFromApi.fromJson(jsonData);
    } else if (response.statusCode == 404) {
      return null; // nu există loc activ
    } else {
      throw Exception('Eroare la obținerea parcării active: ${response.statusCode}');
    }
  }


  static Future<SesiuneParcareDTO?> getSesiuneCurenta(int soferId) async {
    final url = Uri.parse('$baseUrl/api/sesiuni-parcare/curenta/$soferId');
    final response = await http.get(url);

    if (response.statusCode == 200) {
      final data = jsonDecode(response.body);
      return SesiuneParcareDTO.fromJson(data);
    } else if (response.statusCode == 404) {
      return null; // nu există sesiune curentă
    } else {
      throw Exception('Eroare la obținerea sesiunii curente');
    }
  }



}
