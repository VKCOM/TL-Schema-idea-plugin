triangle color:int a:3*[point] = Triangle;

polygon color:int n:# a:n*[point] = Polygon;

point {dim:#} x:dim*[int] = Point dim;

polygon {dim:#} color:int n:# a:n*[(point dim)] = Polygon dim;

picture2d n:# polygons:n*[(polygon 2)] = Picture2d;

pictureXd dim:# n:# polygons:n*[(polygon dim)] = PictureXd;
