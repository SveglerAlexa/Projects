import 'dart:convert';
import 'package:http/http.dart' as http;
import '../models/sofer_model.dart';

class ApiService {
  static const String baseUrl = 'http://10.0.2.2:8080/api/soferi';

  static Future<Sofer?> loginUser(String email, String parola) async {
    final url = Uri.parse('$baseUrl/login');

    try {
      final response = await http.post(url,
          headers: {'Content-Type': 'application/json'},
          body: jsonEncode({'email': email, 'parola': parola}));

      if (response.statusCode == 200) {
        final data = jsonDecode(response.body);
        if (data['success'] == true) return Sofer.fromJson(data);
      }
      return null;
    } catch (e) {
      print('Eroare login: $e');
      return null;
    }
  }

  static Future<Sofer?> registerUser(String nume, String email, String nrInmatriculare, String parola) async {
    final url = Uri.parse('$baseUrl/register');

    try {
      final response = await http.post(url,
          headers: {'Content-Type': 'application/json'},
          body: jsonEncode({
            'nume': nume,
            'email': email,
            'nrInmatriculare': nrInmatriculare,
            'parola': parola
          }));

      if (response.statusCode == 200) {
        final data = jsonDecode(response.body);
        if (data['success'] == true) return Sofer.fromJson(data);
      }
      return null;
    } catch (e) {
      print('Eroare register: $e');
      return null;
    }
  }
}
