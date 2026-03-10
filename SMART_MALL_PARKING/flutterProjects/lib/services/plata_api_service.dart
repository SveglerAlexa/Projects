import 'dart:convert';
import 'package:http/http.dart' as http;
import '../models/plata_request.dart';

class PlataApiService {
  static const String baseUrl = 'http://10.0.2.2:8080';

  static Future<bool> plateste(PlataRequest request) async {
    final url = Uri.parse('$baseUrl/api/plati/plateste?soferId=${request.soferId}&suma=${request.suma}');
    final response = await http.post(url);

    if (response.statusCode == 200) {
      return true;
    } else {
      throw Exception('Eroare la plata: ${response.statusCode}');
    }
  }
}
