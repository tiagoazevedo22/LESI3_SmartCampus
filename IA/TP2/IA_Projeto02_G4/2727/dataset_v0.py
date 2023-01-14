# colocar pelo atribuito "Naive_Bayes..."

import pandas as pd
from sklearn.cluster import KMeans

print("\n### Months_Inactive_12_mon")

# Load data
data = pd.read_csv("BankChurners.csv")

# Select features for clustering
# all columns
#features = ["CLIENTNUM","Attrition_Flag","Customer_Age","Gender","Dependent_count","Education_Level","Marital_Status","Income_Category","Card_Category","Months_on_book","Total_Relationship_Count","Months_Inactive_12_mon","Contacts_Count_12_mon","Credit_Limit","Total_Revolving_Bal","Avg_Open_To_Buy","Total_Amt_Chng_Q4_Q1","Total_Trans_Amt","Total_Trans_Ct","Total_Ct_Chng_Q4_Q1","Avg_Utilization_Ratio","Naive_Bayes_Classifier_Attrition_Flag_Card_Category_Contacts_Count_12_mon_Dependent_count_Education_Level_Months_Inactive_12_mon_1","Naive_Bayes_Classifier_Attrition_Flag_Card_Category_Contacts_Count_12_mon_Dependent_count_Education_Level_Months_Inactive_12_mon_2"]

# filtered columns
features = ["CLIENTNUM","Customer_Age","Dependent_count","Months_on_book","Total_Relationship_Count","Months_Inactive_12_mon","Contacts_Count_12_mon","Credit_Limit","Total_Revolving_Bal","Avg_Open_To_Buy","Total_Amt_Chng_Q4_Q1","Total_Trans_Amt","Total_Trans_Ct","Total_Ct_Chng_Q4_Q1","Avg_Utilization_Ratio","Naive_Bayes_Classifier_Attrition_Flag_Card_Category_Contacts_Count_12_mon_Dependent_count_Education_Level_Months_Inactive_12_mon_1","Naive_Bayes_Classifier_Attrition_Flag_Card_Category_Contacts_Count_12_mon_Dependent_count_Education_Level_Months_Inactive_12_mon_2"]
print("\n### FEATURES");
print(features)

X = data[features]
print("\n### X")
print(X)

print("\n### K-means");
# Train k-means model
kmeans = KMeans(n_clusters=3,n_init=10,algorithm="lloyd", max_iter=1000000)
kmeans.fit(X)
print(kmeans)

# Assign cluster labels to data
data["cluster"] = kmeans.labels_
print("\n### Data")
print(data)

# Count number of customers in each cluster
cluster_counts = data.groupby("cluster")["Months_Inactive_12_mon"].sum()
print("\n### Cluster Counts")
print(cluster_counts) 
# Predict customer based on cluster with the most customers
if cluster_counts[0] > cluster_counts[1]:
    y_pred = 0
else:
    y_pred = 1

# Evaluate accuracy
accuracy = (data["Months_Inactive_12_mon"] == y_pred).mean()
print("\nAccuracy:", accuracy)

