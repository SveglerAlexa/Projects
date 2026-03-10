import 'package:smart_mall_parking/models/parking_spot_from_api.dart';

class RezervareFromApi {
  final int id;
  final ParkingSpotFromApi locParcare;
  final String status; // "rezervat" sau "ocupat"

  RezervareFromApi({
    required this.id,
    required this.locParcare,
    required this.status,
  });

  factory RezervareFromApi.fromJson(Map<String, dynamic> json) {
    return RezervareFromApi(
      id: json['id'] ?? 0,
      locParcare: ParkingSpotFromApi.fromJson(json['locParcare']),
      status: json['status']?.toString() ?? 'rezervat',
    );
  }
}
