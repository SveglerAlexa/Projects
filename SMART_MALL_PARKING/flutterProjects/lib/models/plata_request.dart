class PlataRequest {
  final int soferId;
  final double suma;

  PlataRequest({required this.soferId, required this.suma});

  Map<String, dynamic> toJson() {
    return {
      'soferId': soferId,
      'suma': suma,
    };
  }
}
