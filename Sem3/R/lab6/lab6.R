library(MASS)
x1<-rnorm(20,mean = 0,sd = 9/3)
y1<-rnorm(20,0,9/3)
x2<-rnorm(10,9,9/3)
y2<-rnorm(10,9,9/3)
xy<-cbind(c(x1, x2),c(y1, y2))
xy
cl<-kmeans(xy,2)
n<-30
n.train<-floor(n*0.7)
n.test<-n-n.train
idx.train<-sample(1:n,n.train)
idx.test<-(1:n)[!(1:n %in% idx.train)]
data.train<-xy[idx.train,] 
data.test<-xy[idx.test,] 
cl.cluster<-cl$cluster

# 2
cl.train<-cl.cluster[idx.train]
cl.test<-cl.cluster[idx.test]
model<-qda(data.train, cl.train) 
cl.test_est<-predict(model, data.test)$class

sum(cl.test_est!=cl.test)/n.test
idw<-idx.test[cl.test_est!=cl.test]
idw
plot(xy, type="n")
points(xy[idx.train,],pch=2, col=ifelse(cl.train==1,"blue","green")) 
points(xy[idx.test,],pch=1, col=ifelse(cl.test==1,"blue","green"))
points(xy[idw,],col="red", pch=3)

# 3
idd<-sample(1:n.train,n.train * 0.2)
for(i in idd) {
  if(cl.train[i]==1) cl.train[i]<-2
  else cl.train[i]<-1;
}
model<-qda(data.train, cl.train) 
cl.test_est<-predict(model, data.test)$class
sum(cl.test_est!=cl.test)/n.test
idw<-idx.test[cl.test_est!=cl.test]
idw
plot(xy, type="n")
points(xy[idx.train,],pch=2, col=ifelse(cl.train==1,"blue","green")) 
points(xy[idx.test,],pch=1, col=ifelse(cl.test==1,"blue","green"))
points(xy[idw,],col="red", pch=3)
