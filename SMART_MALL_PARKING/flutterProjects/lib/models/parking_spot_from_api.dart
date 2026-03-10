class ParkingSpotFromApi {
  final int id;
  final String numarLoc;
  final String etaj;
  final bool inauntru;
  final String intrarePreferata;
  final bool pentruPersoaneCuDizabilitati;
  final double x;
  final double y;
  final String status;

  ParkingSpotFromApi({
    required this.id,
    required this.numarLoc,
    required this.etaj,
    required this.inauntru,
    required this.intrarePreferata,
    required this.pentruPersoaneCuDizabilitati,
    required this.x,
    required this.y,
    required this.status,
  });

  factory ParkingSpotFromApi.fromJson(Map<String, dynamic> json) {
    return ParkingSpotFromApi(
      id: json['id'] ?? 0,
      numarLoc: json['numarLoc']?.toString() ?? 'N/A',
      etaj: json['etaj']?.toString() ?? '0',
      inauntru: json['inauntru'] ?? false,
      intrarePreferata: json['intrarePreferata']?.toString() ?? '',
      pentruPersoaneCuDizabilitati: json['pentruPersoaneCuDizabilitati'] ?? false,
      x: (json['x'] as num?)?.toDouble() ?? 0.0,
      y: (json['y'] as num?)?.toDouble() ?? 0.0,
      status: json['status']?.toString() ?? 'liber',
    );
  }




}
