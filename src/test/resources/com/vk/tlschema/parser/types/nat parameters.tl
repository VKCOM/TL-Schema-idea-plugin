point {F:#}
  x:F.0?int
  y:F.1?int
  z:F.2?int
  = Point F;

rectangle {F:#} a:(point F) b:(point F) = Rectangle F;

picture point_fields_mask:# r:(Rectangle point_fields_mask) = Picture;

rectangle2D r:(rectangle (1 + 2)) = Rectangle2D;

rectangle3D r:(rectangle (1 + 2 + 4)) = Rectangle3D;
