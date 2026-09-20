-- CreateEnum
CREATE TYPE "VetApprovalStatus" AS ENUM ('PENDING', 'APPROVED', 'REJECTED', 'SUSPENDED');

-- CreateEnum
CREATE TYPE "ApplicationStatus" AS ENUM ('PENDING', 'APPROVED', 'REJECTED', 'CANCELLED');

-- CreateEnum
CREATE TYPE "FosterAssignmentStatus" AS ENUM ('ACTIVE', 'COMPLETED', 'EXTENDED', 'CANCELLED');

-- AlterTable
ALTER TABLE "media" ADD COLUMN     "medicalRecordId" TEXT;

-- AlterTable
ALTER TABLE "medical_records" ADD COLUMN     "cost" DOUBLE PRECISION,
ADD COLUMN     "diagnosis" TEXT,
ADD COLUMN     "medications" TEXT;

-- CreateTable
CREATE TABLE "vet_profiles" (
    "id" TEXT NOT NULL,
    "userId" TEXT NOT NULL,
    "approvalStatus" "VetApprovalStatus" NOT NULL DEFAULT 'PENDING',
    "licenseNumber" TEXT,
    "clinicName" TEXT,
    "created_at" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updated_at" TIMESTAMP(3) NOT NULL,

    CONSTRAINT "vet_profiles_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "adoption_applications" (
    "id" TEXT NOT NULL,
    "applicantId" TEXT NOT NULL,
    "animalId" TEXT NOT NULL,
    "status" "ApplicationStatus" NOT NULL DEFAULT 'PENDING',
    "motivation" TEXT NOT NULL,
    "housingType" TEXT NOT NULL,
    "householdInformation" TEXT NOT NULL,
    "previousPetExperience" TEXT NOT NULL,
    "existingAnimals" TEXT NOT NULL,
    "additionalNotes" TEXT,
    "created_at" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updated_at" TIMESTAMP(3) NOT NULL,

    CONSTRAINT "adoption_applications_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "foster_applications" (
    "id" TEXT NOT NULL,
    "applicantId" TEXT NOT NULL,
    "animalId" TEXT NOT NULL,
    "status" "ApplicationStatus" NOT NULL DEFAULT 'PENDING',
    "reason" TEXT NOT NULL,
    "housingInformation" TEXT NOT NULL,
    "householdInformation" TEXT NOT NULL,
    "existingAnimals" TEXT NOT NULL,
    "priorAnimalCareExperience" TEXT NOT NULL,
    "availability" TEXT NOT NULL,
    "additionalNotes" TEXT,
    "created_at" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updated_at" TIMESTAMP(3) NOT NULL,

    CONSTRAINT "foster_applications_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "foster_assignments" (
    "id" TEXT NOT NULL,
    "fosterApplicationId" TEXT NOT NULL,
    "startDate" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "expectedEndDate" TIMESTAMP(3) NOT NULL,
    "actualEndDate" TIMESTAMP(3),
    "status" "FosterAssignmentStatus" NOT NULL DEFAULT 'ACTIVE',
    "created_at" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updated_at" TIMESTAMP(3) NOT NULL,

    CONSTRAINT "foster_assignments_pkey" PRIMARY KEY ("id")
);

-- CreateIndex
CREATE UNIQUE INDEX "vet_profiles_userId_key" ON "vet_profiles"("userId");

-- AddForeignKey
ALTER TABLE "media" ADD CONSTRAINT "media_medicalRecordId_fkey" FOREIGN KEY ("medicalRecordId") REFERENCES "medical_records"("id") ON DELETE SET NULL ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "vet_profiles" ADD CONSTRAINT "vet_profiles_userId_fkey" FOREIGN KEY ("userId") REFERENCES "users"("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "adoption_applications" ADD CONSTRAINT "adoption_applications_applicantId_fkey" FOREIGN KEY ("applicantId") REFERENCES "users"("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "adoption_applications" ADD CONSTRAINT "adoption_applications_animalId_fkey" FOREIGN KEY ("animalId") REFERENCES "animals"("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "foster_applications" ADD CONSTRAINT "foster_applications_applicantId_fkey" FOREIGN KEY ("applicantId") REFERENCES "users"("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "foster_applications" ADD CONSTRAINT "foster_applications_animalId_fkey" FOREIGN KEY ("animalId") REFERENCES "animals"("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "foster_assignments" ADD CONSTRAINT "foster_assignments_fosterApplicationId_fkey" FOREIGN KEY ("fosterApplicationId") REFERENCES "foster_applications"("id") ON DELETE CASCADE ON UPDATE CASCADE;
